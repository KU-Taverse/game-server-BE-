package kutaverse.game.websocket.minigame;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotNull;
import kutaverse.game.minigame.dto.MiniGameRequest;
import kutaverse.game.minigame.dto.MiniGameRequestType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;


@Component
@Slf4j
public class MiniGameWebsocketHandler implements org.springframework.web.reactive.socket.WebSocketHandler {
    @Override
    public Mono<Void> handle(WebSocketSession session) {
        ObjectMapper objectMapper = new ObjectMapper();
        session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .map(data -> {
                    try {
                        return objectMapper.readValue(data, MiniGameRequest.class);
                    } catch (Exception e) {
                        log.error("Error handling MiniGameRequest", e);
                        return Mono.empty();
                    }
                })
                .subscribe(data -> {
                    handleMiniGameRequest((MiniGameRequest) data,session);
                });
        return Mono.never();
    }

    private Mono<Void> handleMiniGameRequest(MiniGameRequest miniGameRequest,WebSocketSession session) {
        if (miniGameRequest.getMiniGameRequestType() == MiniGameRequestType.WAIT) {
            MatchingQueue.addPlayer(miniGameRequest.getUserId(), session);
        } else if (miniGameRequest.getMiniGameRequestType() == MiniGameRequestType.RUNNING) {
            GameRoomManager.sendPlayerData(miniGameRequest.getRoomId(), miniGameRequest.getUserId(), miniGameRequest);
        } else {
            GameRoomManager.removeGameRoom(miniGameRequest.getRoomId());
        }
        return Mono.never();
    }
}
