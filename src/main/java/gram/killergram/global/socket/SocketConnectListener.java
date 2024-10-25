package gram.killergram.global.socket;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import gram.killergram.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SocketConnectListener {

    private final JwtTokenProvider jwtTokenProvider;

    @OnConnect
    public void onConnect(SocketIOClient client) {
        String token = client.getHandshakeData().getHttpHeaders().get("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            boolean isRight = jwtTokenProvider.validateToken(token);

            if(!isRight) {
                sendErrorEvent(client, "JWT is missing or invalid.");
                client.disconnect();
            }
        } else {
            sendErrorEvent(client, "JWT is missing or invalid.");
            client.disconnect();
        }
    }

    private void sendErrorEvent(SocketIOClient client, String errorMessage) {
        client.sendEvent("error", errorMessage);
    }
}
