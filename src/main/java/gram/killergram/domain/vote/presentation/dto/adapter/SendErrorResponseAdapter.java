package gram.killergram.domain.vote.presentation.dto.adapter;

import com.corundumstudio.socketio.SocketIOClient;
import gram.killergram.global.error.ErrorCode;
import gram.killergram.global.error.ErrorResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class SendErrorResponseAdapter {

    public void sendErrorResponse(SocketIOClient client, ErrorCode errorCode) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(errorCode.getStatus())
                .message(errorCode.getMessage())
                .build();
        client.sendEvent("error", errorResponse);
    }
}
