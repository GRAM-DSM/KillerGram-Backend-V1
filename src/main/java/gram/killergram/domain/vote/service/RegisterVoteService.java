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
import gram.killergram.domain.vote.presentation.dto.adapter.SendErrorResponseAdapter;
import gram.killergram.domain.vote.presentation.dto.request.RegisterVoteRequest;
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
public class RegisterVoteService {

    private final VoteCrudRepository voteCrudRepository;
    private final VoteUserCrudRepository voteUserRepository;
    private final StudentJpaRepository studentJpaRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserFacade userFacade;
    private final SendErrorResponseAdapter sendErrorResponseAdapter;

    @Transactional
    public void registerVote(SocketIOClient client,
                             RegisterVoteRequest registerVoteRequest, String token) {

        Vote vote = voteCrudRepository.findById(registerVoteRequest.getVoteId())
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

        Sport sport = vote.getSportId();

        boolean isUserInVote = vote.getVoteUser().stream()
                .anyMatch(vu -> vu.getStudentId().getUserId().equals(student.getUserId()));

        if (isUserInVote) {
            sendErrorResponseAdapter.sendErrorResponse(client, ErrorCode.ALREADY_VOTE_REGISTERED);
            throw AlreadyRegisteredVoteException.EXCEPTION;
        }

        if (sport.isPosition() && registerVoteRequest.getPosition() == null) {
            sendErrorResponseAdapter.sendErrorResponse(client, ErrorCode.POSITION_NOT_FOUND);
            throw PositionNotFoundException.EXCEPTION;
        }

        if (!sport.isPosition() && registerVoteRequest.getPosition() != null) {
            sendErrorResponseAdapter.sendErrorResponse(client, ErrorCode.POSITION_NOT_HAS_SPORT);
            throw PositionNotHasSportException.EXCEPTION;
        }

        VoteUser.VoteUserBuilder voteUserBuilder = VoteUser.builder()
                .vote(vote)
                .studentId(student)
                .isAttend(false);

        if (sport.isPosition() && registerVoteRequest.getPosition() != null) {
            int position = registerVoteRequest.getPosition();
            if (position >= 1 && position <= 9) {
                long count = vote.getVoteUser().stream()
                        .filter(vu -> vu.getVote().getVoteId().equals(vote.getVoteId()) && vu.getVotePosition() != null && vu.getVotePosition() == position)
                        .count();

                if (count >= 2) {
                    sendErrorResponseAdapter.sendErrorResponse(client, ErrorCode.POSITION_DUPLICATE);
                    throw PositionDuplicateException.EXCEPTION;
                }
                voteUserBuilder.votePosition(position);
            } else {
                sendErrorResponseAdapter.sendErrorResponse(client, ErrorCode.INVALID_POSITION);
                throw InvalidPositionException.EXCEPTION;
            }
        }
            VoteUser voteUser = voteUserBuilder.build();
            voteUserRepository.save(voteUser);
            vote.addVoteUser(voteUser);
            vote.increaseParticipate();
            voteCrudRepository.save(vote);
    }
}

