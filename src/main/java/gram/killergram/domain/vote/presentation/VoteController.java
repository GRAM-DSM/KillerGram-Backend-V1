package gram.killergram.domain.vote.presentation;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnEvent;
import gram.killergram.domain.vote.presentation.dto.request.CancelVoteRequest;
import gram.killergram.domain.vote.presentation.dto.request.JoinVoteRequest;
import gram.killergram.domain.vote.presentation.dto.request.RegisterVoteRequest;
import gram.killergram.domain.vote.presentation.dto.response.JoinSocketVoteResponse;
import gram.killergram.domain.vote.service.CancelVoteService;
import gram.killergram.domain.vote.service.JoinSocketVoteService;
import gram.killergram.domain.vote.service.RegisterVoteService;
import gram.killergram.global.exception.TokenExpiredException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoteController {

    private final JoinSocketVoteService joinSocketVoteService;
    private final RegisterVoteService registerVoteService;
    private final CancelVoteService cancelVoteService;

    @OnEvent("join")
    public void joinSocketVote(SocketIOClient client, JoinVoteRequest joinVoteRequest) {
        String token = client.get("token");
        if (token == null) {
            client.sendEvent("error", TokenExpiredException.EXCEPTION);
            client.disconnect();
            return;
        }

        JoinSocketVoteResponse response = joinSocketVoteService.joinSocketVote(client, joinVoteRequest, token);
        client.joinRoom(joinVoteRequest.getVoteId().toString());
        client.getNamespace().getRoomOperations(joinVoteRequest.getVoteId().toString()).sendEvent("join", response);
    }

    @OnEvent("register")
    public void registerSocketVote(SocketIOClient client, RegisterVoteRequest request) {
        String token = client.get("token");
        if (token == null) {
            client.sendEvent("error", TokenExpiredException.EXCEPTION);
            client.disconnect();
            return;
        }

        registerVoteService.registerVote(client, request, token);

        // broadcast Changes
        JoinVoteRequest joinVoteRequest = new JoinVoteRequest(request.getVoteId());
        JoinSocketVoteResponse updatedResponse = joinSocketVoteService.joinSocketVote(client, joinVoteRequest, token);

        client.getNamespace().getRoomOperations(request.getVoteId().toString()).sendEvent("join", updatedResponse);
    }

    @OnEvent("cancel")
    public void cancelSocketVote(SocketIOClient client, CancelVoteRequest request) {
        String token = client.get("token");
        if (token == null) {
            client.sendEvent("error", TokenExpiredException.EXCEPTION);
            client.disconnect();
            return;
        }

        cancelVoteService.cancelVote(client, request, token);

        // broadcast Changes
        JoinVoteRequest joinVoteRequest = new JoinVoteRequest(request.getVoteId());
        JoinSocketVoteResponse updatedResponse = joinSocketVoteService.joinSocketVote(client, joinVoteRequest, token);

        client.getNamespace().getRoomOperations(request.getVoteId().toString()).sendEvent("join", updatedResponse);
    }
}
