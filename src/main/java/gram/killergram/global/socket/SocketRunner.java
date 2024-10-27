package gram.killergram.global.socket;

import com.corundumstudio.socketio.SocketIOServer;
import gram.killergram.domain.vote.presentation.VoteController;
import gram.killergram.domain.vote.presentation.dto.request.CancelVoteRequest;
import gram.killergram.domain.vote.presentation.dto.request.JoinVoteRequest;
import gram.killergram.domain.vote.presentation.dto.request.RegisterVoteRequest;
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
        socketIOServer.addEventListener("join", JoinVoteRequest.class,
                (client, joinVoteRequest, ackSender) -> {
                    voteController.joinSocketVote(client, joinVoteRequest);
                });
        socketIOServer.addEventListener("register", RegisterVoteRequest.class,
                (client, registerVoteRequest, ackSender) -> {
                    voteController.registerSocketVote(client, registerVoteRequest);
                });
        socketIOServer.addEventListener("cancel", CancelVoteRequest.class,
                (client, cancelVoteRequest, ackSender) -> {
                    voteController.cancelSocketVote(client, cancelVoteRequest);
                });
        socketIOServer.start();
    }
}