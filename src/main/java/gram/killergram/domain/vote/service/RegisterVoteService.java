package gram.killergram.domain.vote.service;

import com.corundumstudio.socketio.SocketIOClient;
import gram.killergram.domain.sport.domain.Sport;
import gram.killergram.domain.user.domain.Student;
import gram.killergram.domain.user.exception.StudentNotFoundException;
import gram.killergram.domain.user.facade.UserFacade;
import gram.killergram.domain.user.repository.StudentJpaRepository;
import gram.killergram.domain.vote.domain.Vote;
import gram.killergram.domain.vote.domain.VoteUser;
import gram.killergram.domain.vote.exception.PositionNotFoundException;
import gram.killergram.domain.vote.exception.VoteIsEndException;
import gram.killergram.domain.vote.exception.VoteNotFoundException;
import gram.killergram.domain.vote.presentation.dto.request.RegisterVoteRequest;
import gram.killergram.domain.vote.presentation.dto.response.JoinSocketVoteResponse;
import gram.killergram.domain.vote.repository.VoteCrudRepository;
import gram.killergram.domain.vote.repository.VoteUserCrudRepository;
import gram.killergram.global.exception.TokenExpiredException;
import gram.killergram.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegisterVoteService {

    private final VoteCrudRepository voteCrudRepository;
    private final VoteUserCrudRepository voteUserRepository;
    private final StudentJpaRepository studentJpaRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserFacade userFacade;
    private final JoinSocketVoteService joinSocketVoteService;

    @Transactional
    public void registerVote(SocketIOClient client,
                             RegisterVoteRequest registerVoteRequest, String token) {
        Vote vote = voteCrudRepository.findById(registerVoteRequest.getVoteId())
                .orElseThrow(() -> {
                    client.sendEvent("error", VoteNotFoundException.EXCEPTION);
                    return VoteNotFoundException.EXCEPTION;
                });

        if(vote.isEnd()) {
            client.sendEvent("error", VoteIsEndException.EXCEPTION);
            throw VoteIsEndException.EXCEPTION;
        }

        if(token == null) {
            client.sendEvent("error", TokenExpiredException.EXCEPTION);
            throw TokenExpiredException.EXCEPTION;
        }

        String userAccountId;
        if(jwtTokenProvider.validateToken(token)) {
            userAccountId = jwtTokenProvider.getAuthentication(token).getName();
        } else throw TokenExpiredException.EXCEPTION;

        UUID userId =  userFacade.getUserId(userAccountId);
        Student student = studentJpaRepository.findById(userId)
                .orElseThrow(() -> {
                    client.sendEvent("error", StudentNotFoundException.EXCEPTION);
                    return StudentNotFoundException.EXCEPTION;
                });

        Sport sport = vote.getSportId();

        if(sport.isPosition()) {
            if(registerVoteRequest.getPosition() == null) {
                client.sendEvent("error", PositionNotFoundException.EXCEPTION);
                throw PositionNotFoundException.EXCEPTION;
            }

            VoteUser voteUser = VoteUser.builder()
                    .vote(vote)
                    .votePosition(registerVoteRequest.getPosition())
                    .student(student)
                    .isAttend(false)
                    .build();

            voteUserRepository.save(voteUser);
            vote.addVoteUser(voteUser);
            vote.increaseParticipate();
            voteCrudRepository.save(vote);

            JoinSocketVoteResponse joinResponse = joinSocketVoteService.joinSocketVote(client,
                    token, registerVoteRequest.getVoteId());
            client.getNamespace().getBroadcastOperations().sendEvent("joinVote", joinResponse);

        } else {
            if(registerVoteRequest.getPosition() != null) {
                client.sendEvent("error", PositionNotFoundException.EXCEPTION);
                throw PositionNotFoundException.EXCEPTION;
            }

            VoteUser voteUser = VoteUser.builder()
                    .vote(vote)
                    .student(student)
                    .isAttend(false)
                    .build();

            voteUserRepository.save(voteUser);
            vote.addVoteUser(voteUser);
            vote.increaseParticipate();
            voteCrudRepository.save(vote);

            JoinSocketVoteResponse joinResponse = joinSocketVoteService.joinSocketVote(client,
                    token, registerVoteRequest.getVoteId());
            client.getNamespace().getBroadcastOperations().sendEvent("joinVote", joinResponse);
        }
    }
}
