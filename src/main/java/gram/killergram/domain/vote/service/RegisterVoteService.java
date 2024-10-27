package gram.killergram.domain.vote.service;

import com.corundumstudio.socketio.SocketIOClient;
import gram.killergram.domain.sport.domain.Sport;
import gram.killergram.domain.user.domain.Student;
import gram.killergram.domain.user.exception.StudentNotFoundException;
import gram.killergram.domain.user.facade.UserFacade;
import gram.killergram.domain.user.repository.StudentJpaRepository;
import gram.killergram.domain.vote.domain.Vote;
import gram.killergram.domain.vote.domain.VoteUser;
import gram.killergram.domain.vote.exception.*;
import gram.killergram.domain.vote.presentation.dto.request.RegisterVoteRequest;
import gram.killergram.domain.vote.repository.VoteCrudRepository;
import gram.killergram.domain.vote.repository.VoteUserCrudRepository;
import gram.killergram.global.error.ErrorCode;
import gram.killergram.global.error.ErrorResponse;
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

    @Transactional
    public void registerVote(SocketIOClient client,
                             RegisterVoteRequest registerVoteRequest, String token) {
        Vote vote = voteCrudRepository.findById(registerVoteRequest.getVoteId())
                .orElseThrow(() -> {
                    sendErrorResponse(client, ErrorCode.VOTE_NOT_FOUND);
                    return VoteNotFoundException.EXCEPTION;
                });

        if (vote.isEnd()) {
            sendErrorResponse(client, ErrorCode.VOTE_IS_END);
            throw VoteIsEndException.EXCEPTION;
        }

        String userAccountId;
        if (token != null || jwtTokenProvider.validateToken(token)) {
            userAccountId = jwtTokenProvider.getAuthentication(token).getName();
        } else {
            sendErrorResponse(client, ErrorCode.EXPIRED_TOKEN);
            throw TokenExpiredException.EXCEPTION;
        }

        UUID userId = userFacade.getUserId(userAccountId);
        Student student = studentJpaRepository.findById(userId)
                .orElseThrow(() -> {
                    sendErrorResponse(client, ErrorCode.STUDENT_NOT_FOUND);
                    return StudentNotFoundException.EXCEPTION;
                });

        Sport sport = vote.getSportId();

        boolean isUserInVote = vote.getVoteUser().stream()
                .anyMatch(vu -> vu.getStudent().getUserId().equals(student.getUserId()));

        if (isUserInVote) {
            sendErrorResponse(client, ErrorCode.ALREADY_VOTE_REGISTERED);
            throw AlreadyRegisteredVoteException.EXCEPTION;
        }

        if (sport.isPosition() && registerVoteRequest.getPosition() == null) {
            sendErrorResponse(client, ErrorCode.POSITION_NOT_FOUND);
            throw PositionNotFoundException.EXCEPTION;
        }

        if (!sport.isPosition() && registerVoteRequest.getPosition() != null) {
            sendErrorResponse(client, ErrorCode.POSITION_NOT_HAS_SPORT);
            throw PositionNotHasSportException.EXCEPTION;
        }

        VoteUser.VoteUserBuilder voteUserBuilder = VoteUser.builder()
                .vote(vote)
                .student(student)
                .isAttend(false);

        if (sport.isPosition()) voteUserBuilder.votePosition(registerVoteRequest.getPosition());

        VoteUser voteUser = voteUserBuilder.build();
        voteUserRepository.save(voteUser);
        vote.addVoteUser(voteUser);
    }

        private void sendErrorResponse(SocketIOClient client, ErrorCode errorCode) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(errorCode.getStatus())
                .message(errorCode.getMessage())
                .build();
        client.sendEvent("error", errorResponse);
    }
}

