package kutaverse.game.websocket.taggame.util;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketSession;

import java.util.List;
import java.util.Map;

@Getter
public class TagGameRoom {
    private final String roomId;
    private final List<Map.Entry<String, WebSocketSession>> players;
    public TagGameRoom(String roomId, List<Map.Entry<String, WebSocketSession>> players) {
        this.roomId = roomId;
        this.players = players;
    }

    public void delete(){
        for (Map.Entry<String, WebSocketSession> player : players) {
            player.getValue().close().subscribe();
        }
    }

}
