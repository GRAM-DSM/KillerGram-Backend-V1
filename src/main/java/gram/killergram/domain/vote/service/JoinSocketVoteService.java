package gram.killergram.domain.vote.service;

import com.corundumstudio.socketio.SocketIOClient;
import gram.killergram.domain.user.domain.Student;
import gram.killergram.domain.user.exception.StudentNotFoundException;
import gram.killergram.domain.user.facade.UserFacade;
import gram.killergram.domain.user.repository.StudentJpaRepository;
import gram.killergram.domain.vote.domain.Vote;
import gram.killergram.domain.vote.domain.VoteUser;
import gram.killergram.domain.vote.exception.VoteNotFoundException;
import gram.killergram.domain.vote.presentation.dto.adapter.StudentAdapter;
import gram.killergram.domain.vote.presentation.dto.request.JoinVoteRequest;
import gram.killergram.domain.vote.presentation.dto.response.JoinSocketVoteResponse;
import gram.killergram.domain.vote.repository.VoteCrudRepository;
import gram.killergram.global.error.ErrorCode;
import gram.killergram.global.error.ErrorResponse;
import gram.killergram.global.exception.TokenExpiredException;
import gram.killergram.global.security.jwt.JwtTokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JoinSocketVoteService {

    private final VoteCrudRepository voteCrudRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final StudentJpaRepository studentJpaRepository;
    private final UserFacade userFacade;

    @Transactional
    public JoinSocketVoteResponse joinSocketVote(SocketIOClient client, JoinVoteRequest joinVoteRequest, String token) {
        Vote vote = voteCrudRepository.findById(joinVoteRequest.getVoteId())
                .orElseThrow(() -> {
                    sendErrorResponse(client, ErrorCode.VOTE_NOT_FOUND);
                    return VoteNotFoundException.EXCEPTION;
                });

        String managerEmail = vote.getSportId().getManagerEmail();

        if (token == null || !jwtTokenProvider.validateToken(token)) {
            sendErrorResponse(client, ErrorCode.EXPIRED_TOKEN);
            throw TokenExpiredException.EXCEPTION;
        }

        String userAccountId = jwtTokenProvider.getAuthentication(token).getName();
        boolean isAdmin = managerEmail.equals(userAccountId);

        UUID userId = userFacade.getUserId(userAccountId);
        Student student = studentJpaRepository.findById(userId)
                .orElseThrow(() -> {
                    sendErrorResponse(client, ErrorCode.STUDENT_NOT_FOUND);
                    return StudentNotFoundException.EXCEPTION;
                });

        Set<VoteUser> voteUser = vote.getVoteUser();
        Set<StudentAdapter> studentAdapters = voteUser.stream()
                .map(voteUser1 -> new StudentAdapter(
                        voteUser1.getStudent().getName(),
                        voteUser1.getStudent().getSchoolNumber()))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        boolean isUserInVote = voteUser.stream()
                .anyMatch(vu -> vu.getStudent().getUserId().equals(student.getUserId()));

        return JoinSocketVoteResponse.builder()
                .sportName(vote.getSportId().getSportName())
                .ability(student.getAbility())
                .voteStudents(studentAdapters)
                .timeSlot(vote.getTimeSlot())
                .participate(vote.getParticipate())
                .isJoinMe(isUserInVote)
                .isEnd(vote.isEnd())
                .isAdmin(isAdmin)
                .build();
    }

    private void sendErrorResponse(SocketIOClient client, ErrorCode errorCode) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(errorCode.getStatus())
                .message(errorCode.getMessage())
                .build();
        client.sendEvent("error", errorResponse);
    }
}

