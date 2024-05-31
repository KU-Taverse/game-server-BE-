package kutaverse.game.websocket.map.handler;

import kutaverse.game.websocket.map.dto.request.UserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResponseHandler implements WebSocketHandler {
    @Override
    public void handle(UserRequestDto e) {
    }
}
