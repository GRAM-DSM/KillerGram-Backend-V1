package gram.killergram.global.socket;

import com.corundumstudio.socketio.SocketIOServer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import gram.killergram.domain.vote.presentation.VoteController;
import gram.killergram.domain.vote.presentation.dto.request.RegisterVoteRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;

@Component
@RequiredArgsConstructor
public class SocketRunner implements CommandLineRunner {

    private final SocketIOServer socketIOServer;
    private final SocketConnectListener socketConnectListener;
    private final VoteController voteController;

    @Override
    public void run(String[] args) {
        socketIOServer.addConnectListener(socketConnectListener::onConnect);
        socketIOServer.addEventListener("join", JsonNode.class, (client, data, ackSender) -> {
            voteController.joinSocketVote(client, data);
        });
        socketIOServer.addEventListener("register", RegisterVoteRequest.class,
                (client, registerVoteRequest, ackSender) -> {
            voteController.registerSocketVote(client, registerVoteRequest);
        });
        socketIOServer.start();
    }
}
