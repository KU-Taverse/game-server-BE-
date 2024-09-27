package kutaverse.game.websocket.taggame.util;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketSession;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class TagGameRoom {
    private final String roomId;
    private final Map<String, WebSocketSession> players;
    //private final List<Map.Entry<String, WebSocketSession>> players;
    public TagGameRoom(String roomId, List<Map.Entry<String, WebSocketSession>> players) {
        this.roomId = roomId;
        this.players = new ConcurrentHashMap<>();
        for (Map.Entry<String, WebSocketSession> player : players) {
            this.players.put(player.getKey(),player.getValue());
        }

    }

    public void delete(){
        for (WebSocketSession webSocketSession : players.values()) {
            webSocketSession.close().subscribe();
        }
    }

    public void deleteUser(String userId){
        players.remove(userId);
    }
}
