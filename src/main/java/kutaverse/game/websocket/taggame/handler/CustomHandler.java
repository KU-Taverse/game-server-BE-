package kutaverse.game.websocket.taggame.handler;

import org.springframework.web.reactive.socket.WebSocketSession;

public interface CustomHandler {

    void handler(Object o, WebSocketSession session);
}
