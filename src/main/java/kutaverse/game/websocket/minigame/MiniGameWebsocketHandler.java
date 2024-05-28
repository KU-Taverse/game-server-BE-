package kutaverse.game.websocket.minigame;

import com.fasterxml.jackson.databind.ObjectMapper;
import kutaverse.game.map.dto.UserRequestDto;
import kutaverse.game.minigame.dto.MiniGameRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Slf4j
public class MiniGameWebsocketHandler implements org.springframework.web.reactive.socket.WebSocketHandler {
    @Override
    public Mono<Void> handle(WebSocketSession session) {
        ObjectMapper objectMapper=new ObjectMapper();
        session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .map(data -> {
                    try {
                        MiniGameRequest miniGameRequest = objectMapper.readValue(data, MiniGameRequest.class);  // JSON을 RoomDto로 변환
                        return miniGameRequest;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .flatMap(miniGameRequest ->{
                    if(miniGameRequest.getRoomId() == 0){
                        MatchingQueue.addPlayer(miniGameRequest.getUserId(),session);
                        return Mono.empty();
                    }
                    // 여기는 나중에 만약 방 번호가 있으면 다른 유저에게 해당 값 전달
                    return Mono.empty();
                })
                .then();
        return null;
    }
}
