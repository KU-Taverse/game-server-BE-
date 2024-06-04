package kutaverse.game.websocket.minigame;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kutaverse.game.minigame.dto.MiniGameRequest;
import lombok.Getter;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;



@Getter
public class GameRoom {
    private final String roomId;

    private final Map<String, WebSocketSession> players = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GameRoom(String roomId) {
        this.roomId = roomId;
    }

    public void addPlayer(String userId, WebSocketSession webSocketSession){
        players.put(userId,webSocketSession);
    }


    public void removePlayer(String userId){
        players.remove(userId);
    }

    // 이건 나중에 필요한 값들을 보내는 용도
    public void broadcastMessage(String message) {
        players.values().forEach(s ->{
            s.send(Mono.just(s.textMessage(message))).subscribe();
        });
    }

    public void sendDataToOther(String userId, MiniGameRequest data) throws JsonProcessingException {
        String dataJson = objectMapper.writeValueAsString(data);
        players.entrySet().stream()
                .filter(entry -> !entry.getKey().equals(userId))
                .forEach(entry -> {
                    WebSocketSession session = entry.getValue();
                    session.send(Mono.just(session.textMessage(dataJson))).subscribe();
                });
    }

    // 상대방이 나간 처리
    public void handlePlayerLeft(String userId){
        players.entrySet().stream()
                .filter(entry -> !entry.getKey().equals(userId))
                .forEach(entry -> {
                    WebSocketSession session = entry.getValue();
                    session.send(Mono.just(session.textMessage("상대방이 나갔습니다."))).subscribe();
                });
    }
}
