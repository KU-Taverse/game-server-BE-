package kutaverse.game.websocket.minigame;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketSession;

import java.util.AbstractMap;
import java.util.ArrayDeque;
import java.util.Map;

// Queue를 통해 플레이어들을 저장해 둘 예정


@Component
public class MatchingQueue {
    private static final ArrayDeque<Map.Entry<String, WebSocketSession>> queueing = new ArrayDeque<>();

    public static void addPlayer(String userId, WebSocketSession webSocketSession){
        queueing.offer(new AbstractMap.SimpleEntry<>(userId,webSocketSession));
    }

    public static Map.Entry<String, WebSocketSession> getPlayer(){
        return queueing.poll();
    }

    public static int queueingSize(){
        return queueing.size();
    }

    public static void requeue(Map.Entry<String, WebSocketSession> player){
        queueing.addFirst(player);
    }


}
