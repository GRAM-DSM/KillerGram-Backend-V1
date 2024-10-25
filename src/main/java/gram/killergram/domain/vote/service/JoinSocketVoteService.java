package gram.killergram.domain.vote.service;

import gram.killergram.domain.user.domain.Student;
import gram.killergram.domain.user.exception.StudentNotFoundException;
import gram.killergram.domain.user.facade.UserFacade;
import gram.killergram.domain.user.repository.StudentJpaRepository;
import gram.killergram.domain.vote.domain.Vote;
import gram.killergram.domain.vote.domain.VoteUser;
import gram.killergram.domain.vote.exception.VoteNotFoundException;
import gram.killergram.domain.vote.presentation.dto.response.JoinSocketVoteResponse;
import gram.killergram.domain.vote.repository.VoteCrudRepository;
import gram.killergram.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class JoinSocketVoteService {

    private final VoteCrudRepository voteCrudRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final StudentJpaRepository studentJpaRepository;
    private final UserFacade userFacade;

    public JoinSocketVoteResponse joinSocketVote(String token , String voteId) {
        Vote vote = voteCrudRepository.findById(UUID.fromString(voteId))
                .orElseThrow(() -> VoteNotFoundException.EXCEPTION);

        boolean isAdmin = false;

        String managerEmail = vote.getSportId().getManagerEmail();

        if (token.startsWith("Bearer ")) token = token.substring(7).trim();
        String userAccountId = jwtTokenProvider.getAuthentication(token).getName();
        log.info(userAccountId);

        if(managerEmail.equals(userAccountId)) isAdmin = true;

        UUID userId =  userFacade.getUserId(userAccountId);
        Student student = studentJpaRepository.findById(userId)
                .orElseThrow(() -> StudentNotFoundException.EXCEPTION);

        List<VoteUser> voteUser = vote.getVoteUser();

        boolean isUserInVote = voteUser.stream()
                .anyMatch(vu -> vu.getStudent().equals(student));



        return JoinSocketVoteResponse.builder()
                .sportName(vote.getSportId().getSportName())
                .ability(student.getAbility())
                .voteStudents(vote.getVoteUser())
                .timeSlot(vote.getTimeSlot())
                .participate(vote.getParticipate())
                .isJoinMe(isUserInVote)
                .isEnd(vote.isEnd())
                .isAdmin(isAdmin)
                .build();
    }
}
