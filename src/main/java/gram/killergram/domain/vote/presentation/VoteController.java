package gram.killergram.domain.vote.presentation;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnEvent;
import gram.killergram.domain.vote.service.JoinSocketVoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoteController {

    private final JoinSocketVoteService joinSocketVoteService;

    @OnEvent("join")
    public void joinSocketVote(SocketIOClient client) {
        String token = client.getHandshakeData().getHttpHeaders().get("Authorization");
        String roomId = client.getHandshakeData().getSingleUrlParam("roomId");
        client.sendEvent("joinVote",joinSocketVoteService.joinSocketVote(token ,roomId));
    }
}
