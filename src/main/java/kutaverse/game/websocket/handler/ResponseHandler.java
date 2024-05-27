package kutaverse.game.websocket.handler;

import kutaverse.game.map.dto.UserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResponseHandler implements WebSocketHandler {
    @Override
    public void handle(UserRequestDto e) {
    }
}
