package gram.killergram.domain.vote.service;

import com.corundumstudio.socketio.SocketIOClient;
import gram.killergram.domain.user.domain.Student;
import gram.killergram.domain.user.exception.StudentNotFoundException;
import gram.killergram.domain.user.facade.UserFacade;
import gram.killergram.domain.user.repository.StudentJpaRepository;
import gram.killergram.domain.vote.domain.Vote;
import gram.killergram.domain.vote.domain.VoteUser;
import gram.killergram.domain.vote.exception.VoteNotFoundException;
import gram.killergram.domain.vote.presentation.dto.response.JoinSocketVoteResponse;
import gram.killergram.domain.vote.repository.VoteCrudRepository;
import gram.killergram.global.exception.TokenExpiredException;
import gram.killergram.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JoinSocketVoteService {

    private final VoteCrudRepository voteCrudRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final StudentJpaRepository studentJpaRepository;
    private final UserFacade userFacade;

    @Transactional
    public JoinSocketVoteResponse joinSocketVote(SocketIOClient client, String token , String voteId) {
        Vote vote = voteCrudRepository.findById(UUID.fromString(voteId))
                .orElseThrow(() -> {
                    client.sendEvent("error", VoteNotFoundException.EXCEPTION);
                    return VoteNotFoundException.EXCEPTION;
                });


        boolean isAdmin = false;

        String managerEmail = vote.getSportId().getManagerEmail();

        if(token == null) {
            client.sendEvent("error", TokenExpiredException.EXCEPTION);
            throw TokenExpiredException.EXCEPTION;
        }

        String userAccountId;
        if(jwtTokenProvider.validateToken(token)) {
            userAccountId = jwtTokenProvider.getAuthentication(token).getName();
        } else throw TokenExpiredException.EXCEPTION;

        if(managerEmail.equals(userAccountId)) isAdmin = true;

        UUID userId =  userFacade.getUserId(userAccountId);
        Student student = studentJpaRepository.findById(userId)
                .orElseThrow(() -> {
                    client.sendEvent("error", StudentNotFoundException.EXCEPTION);
                    return StudentNotFoundException.EXCEPTION;
                });

        LinkedHashSet<VoteUser> voteUser = vote.getVoteUser();

        boolean isUserInVote = voteUser.contains(student);

        return JoinSocketVoteResponse.builder()
                .sportName(vote.getSportId().getSportName())
                .ability(student.getAbility())
                .voteStudents(voteUser)
                .timeSlot(vote.getTimeSlot())
                .participate(vote.getParticipate())
                .isJoinMe(isUserInVote)
                .isEnd(vote.isEnd())
                .isAdmin(isAdmin)
                .build();
    }
}
