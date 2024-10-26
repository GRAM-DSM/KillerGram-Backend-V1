package gram.killergram.domain.vote.presentation;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.fasterxml.jackson.databind.JsonNode;
import gram.killergram.domain.vote.presentation.dto.request.RegisterVoteRequest;
import gram.killergram.domain.vote.presentation.dto.response.JoinSocketVoteResponse;
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

    @OnEvent("join")
    public void joinSocketVote(SocketIOClient client, JsonNode node) {
        String token = client.get("token");
        if(token == null) {
            client.sendEvent("error", TokenExpiredException.EXCEPTION);
            client.disconnect();
            return;
        }
        String roomId = node.get("room_id").asText();
        JoinSocketVoteResponse response = joinSocketVoteService.joinSocketVote(client, token ,roomId);
        client.sendEvent("joinVote", response);
    }

    @OnEvent("register")
    public void registerSocketVote(SocketIOClient client, RegisterVoteRequest request) {
        String token = client.get("token");
        if(token == null) {
            client.sendEvent("error", TokenExpiredException.EXCEPTION);
            client.disconnect();
            return;
        }
        registerVoteService.registerVote(client, request, token);
    }
}
