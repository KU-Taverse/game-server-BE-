package kutaverse.game.websocket.taggame;

import com.fasterxml.jackson.databind.ObjectMapper;
import kutaverse.game.websocket.taggame.handler.CustomHandler;
import kutaverse.game.websocket.taggame.handler.CustomHandlerMapping;
import kutaverse.game.websocket.taggame.util.TagGameRequestUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class TagGameWebSocketHandler implements WebSocketHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * request로 반환후 handler를 실행한다.
     * @param session the session to handle
     * @return
     */
    @Override
    public Mono<Void> handle(WebSocketSession session) {
        session.receive()
            .map(message -> {
                String payloadAsText = message.getPayloadAsText();
                return TagGameRequestUtil.fromWebsocketMessage(payloadAsText);
            })
            .doOnNext(tagGameRequest->{
                CustomHandler customHandler = CustomHandlerMapping.getHandler(tagGameRequest.getTagGameStatus());
                customHandler.handler(tagGameRequest,session);
            }).subscribe();
        return Mono.never();
    }
}