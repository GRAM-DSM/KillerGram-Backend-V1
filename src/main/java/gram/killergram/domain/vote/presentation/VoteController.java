package gram.killergram.domain.vote.presentation;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnEvent;
import gram.killergram.domain.vote.presentation.dto.request.JoinVoteRequest;
import gram.killergram.domain.vote.presentation.dto.request.RegisterVoteRequest;
import gram.killergram.domain.vote.presentation.dto.response.JoinSocketVoteResponse;
import gram.killergram.domain.vote.service.JoinSocketVoteService;
import gram.killergram.domain.vote.service.RegisterVoteService;
import gram.killergram.global.exception.TokenExpiredException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoteController {

    private final JoinSocketVoteService joinSocketVoteService;
    private final RegisterVoteService registerVoteService;

    @OnEvent("join")
    public void joinSocketVote(SocketIOClient client, JoinVoteRequest joinVoteRequest) {
        String token = client.get("token");
        if (token == null) {
            client.sendEvent("error", TokenExpiredException.EXCEPTION);
            client.disconnect();
            return;
        }

        JoinSocketVoteResponse response = joinSocketVoteService.joinSocketVote(client, joinVoteRequest, token);
        client.sendEvent("joinVote", response);
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

        JoinVoteRequest joinVoteRequest = new JoinVoteRequest(request.getVoteId());
        JoinSocketVoteResponse updatedResponse = joinSocketVoteService.joinSocketVote(client, joinVoteRequest, token);

        client.getNamespace().getBroadcastOperations().sendEvent("joinVote", updatedResponse);
    }
}
