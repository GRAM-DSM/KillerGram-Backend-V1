package gram.killergram.global.socket;

import com.corundumstudio.socketio.SocketIOServer;
import com.fasterxml.jackson.databind.JsonNode;
import gram.killergram.domain.vote.presentation.VoteController;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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
        socketIOServer.start();
    }
}
