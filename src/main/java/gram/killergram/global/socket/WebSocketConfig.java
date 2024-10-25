package gram.killergram.global.socket;

import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.Transport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebSocketConfig {

    @Value("${socket.port}")
    private int port;

    @Value("${socket.host}")
    private String host;

    @Bean
    public SocketIOServer socketIOServer() {

        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setReuseAddress(true);


        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();

        config.setHostname(host);
        config.setPort(port);
        config.setTransports(Transport.WEBSOCKET, Transport.POLLING);
        config.setOrigin("*");
        config.setSocketConfig(socketConfig);

        return new SocketIOServer(config);
    }
}
