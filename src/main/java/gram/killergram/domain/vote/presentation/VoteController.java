package gram.killergram.domain.vote.presentation;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.fasterxml.jackson.databind.JsonNode;
import gram.killergram.domain.vote.presentation.dto.response.JoinSocketVoteResponse;
import gram.killergram.domain.vote.service.JoinSocketVoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoteController {

    private final JoinSocketVoteService joinSocketVoteService;

    @OnEvent("join")
    public void joinSocketVote(SocketIOClient client, JsonNode node) {
        String token = client.get("token");
        String roomId = node.get("room_id").asText();
        JoinSocketVoteResponse response = joinSocketVoteService.joinSocketVote(token ,roomId);
        client.sendEvent("joinVote", response);
    }
}
