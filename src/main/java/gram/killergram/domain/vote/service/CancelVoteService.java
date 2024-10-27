package gram.killergram.domain.vote.service;

import com.corundumstudio.socketio.SocketIOClient;
import gram.killergram.domain.user.domain.Student;
import gram.killergram.domain.user.exception.StudentNotFoundException;
import gram.killergram.domain.user.facade.UserFacade;
import gram.killergram.domain.user.repository.StudentJpaRepository;
import gram.killergram.domain.vote.domain.Vote;
import gram.killergram.domain.vote.domain.VoteUser;
import gram.killergram.domain.vote.exception.NoRegisteredMyUserFound;
import gram.killergram.domain.vote.exception.VoteIsEndException;
import gram.killergram.domain.vote.exception.VoteNotFoundException;
import gram.killergram.domain.vote.presentation.dto.adapter.SendErrorResponseAdapter;
import gram.killergram.domain.vote.presentation.dto.request.CancelVoteRequest;
import gram.killergram.domain.vote.repository.VoteCrudRepository;
import gram.killergram.domain.vote.repository.VoteUserCrudRepository;
import gram.killergram.global.error.ErrorCode;
import gram.killergram.global.exception.TokenExpiredException;
import gram.killergram.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CancelVoteService {

    private final VoteCrudRepository voteCrudRepository;
    private final SendErrorResponseAdapter sendErrorResponseAdapter;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserFacade userFacade;
    private final StudentJpaRepository studentJpaRepository;
    private final VoteUserCrudRepository voteUserCrudRepository;

    @Transactional
    public void cancelVote(SocketIOClient client, CancelVoteRequest cancelVoteRequest,
                           String token) {
        Vote vote = voteCrudRepository.findById(cancelVoteRequest.getVoteId())
                .orElseThrow(() -> {
                    sendErrorResponseAdapter.sendErrorResponse(client, ErrorCode.VOTE_NOT_FOUND);
                    return VoteNotFoundException.EXCEPTION;
                });

        if (vote.isEnd()) {
            sendErrorResponseAdapter.sendErrorResponse(client, ErrorCode.VOTE_IS_END);
            throw VoteIsEndException.EXCEPTION;
        }

        String userAccountId;
        if (token != null || jwtTokenProvider.validateToken(token)) {
            userAccountId = jwtTokenProvider.getAuthentication(token).getName();
        } else {
            sendErrorResponseAdapter.sendErrorResponse(client, ErrorCode.EXPIRED_TOKEN);
            throw TokenExpiredException.EXCEPTION;
        }

        UUID userId = userFacade.getUserId(userAccountId);
        Student student = studentJpaRepository.findById(userId)
                .orElseThrow(() -> {
                    sendErrorResponseAdapter.sendErrorResponse(client, ErrorCode.STUDENT_NOT_FOUND);
                    return StudentNotFoundException.EXCEPTION;
                });

        boolean isUserInVote = vote.getVoteUser().stream()
                .anyMatch(vu -> vu.getStudentId().getUserId().equals(userId));

        if(!isUserInVote) {
            sendErrorResponseAdapter.sendErrorResponse(client, ErrorCode.NO_REGISTERED_MY_USER_FOUND);
            throw NoRegisteredMyUserFound.EXCEPTION;
        }

        VoteUser targetVoteUser = vote.getVoteUser().stream()
                .filter(vu -> vu.getStudentId().getUserId().equals(userId))
                .findFirst()
                .orElseThrow(() -> {
                    sendErrorResponseAdapter.sendErrorResponse(client, ErrorCode.NO_REGISTERED_MY_USER_FOUND);
                    throw NoRegisteredMyUserFound.EXCEPTION;
                });

        vote.getVoteUser().remove(targetVoteUser);
        vote.decreaseParticipate();
        voteCrudRepository.save(vote);

        VoteUser voteUser = voteUserCrudRepository.findByStudentId(student)
                .orElseThrow(() -> {
                    sendErrorResponseAdapter.sendErrorResponse(client, ErrorCode.NO_REGISTERED_MY_USER_FOUND);
                    return NoRegisteredMyUserFound.EXCEPTION;
                });
        voteUserCrudRepository.save(voteUser);
    }
}
