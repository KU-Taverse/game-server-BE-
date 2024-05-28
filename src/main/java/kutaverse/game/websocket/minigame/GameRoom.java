package kutaverse.game.websocket.minigame;

import lombok.Getter;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class GameRoom {
    private final String roomId;

    private final Map<String, WebSocketSession> players = new ConcurrentHashMap<>();

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
    public void sendMessage(String message) {
        players.values().forEach(s ->{
            s.send(Mono.just(s.textMessage(message))).subscribe();
        });
    }
}
