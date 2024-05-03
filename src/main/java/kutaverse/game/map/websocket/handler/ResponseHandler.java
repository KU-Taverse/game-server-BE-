package kutaverse.game.map.websocket.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResponseHandler implements WebSocketHandler {
    @Override
    public String handle(String e) {
        return null;
    }
}
