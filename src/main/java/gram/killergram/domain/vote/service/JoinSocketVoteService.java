package gram.killergram.domain.vote.service;

import com.corundumstudio.socketio.SocketIOClient;
import gram.killergram.domain.user.domain.Student;
import gram.killergram.domain.user.facade.UserFacade;
import gram.killergram.domain.user.repository.StudentJpaRepository;
import gram.killergram.domain.vote.domain.Vote;
import gram.killergram.domain.vote.domain.VoteUser;
import gram.killergram.domain.vote.presentation.dto.response.JoinSocketVoteResponse;
import gram.killergram.domain.vote.repository.VoteCrudRepository;
import gram.killergram.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class JoinSocketVoteService {

    private final VoteCrudRepository voteCrudRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final StudentJpaRepository studentJpaRepository;
    private final UserFacade userFacade;

    @Transactional
    public JoinSocketVoteResponse joinSocketVote(SocketIOClient client, String token , String voteId) {
        Optional<Vote> vote = voteCrudRepository.findById(UUID.fromString(voteId));
        if(vote.isEmpty()) client.sendEvent("404", "Vote Not Found");

        boolean isAdmin = false;

        String managerEmail = vote.get().getSportId().getManagerEmail();
        String userAccountId = jwtTokenProvider.getAuthentication(token).getName();

        if(managerEmail.equals(userAccountId)) isAdmin = true;

        UUID userId =  userFacade.getUserId(userAccountId);
        Optional<Student> student = studentJpaRepository.findById(userId);
        if(student.isEmpty()) client.sendEvent("404", "Student Not Found");

        List<VoteUser> voteUser = vote.get().getVoteUser() != null ? vote.get().getVoteUser() : Collections.emptyList();

        boolean isUserInVote = voteUser.stream()
                .anyMatch(vu -> vu.getStudent().equals(student));

        return JoinSocketVoteResponse.builder()
                .sportName(vote.get().getSportId().getSportName())
                .ability(student.get().getAbility())
                .voteStudents(voteUser)
                .timeSlot(vote.get().getTimeSlot())
                .participate(vote.get().getParticipate())
                .isJoinMe(isUserInVote)
                .isEnd(vote.get().isEnd())
                .isAdmin(isAdmin)
                .build();
    }
}
