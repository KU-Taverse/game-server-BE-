package kutaverse.game.websocket.minigame;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketSession;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

// Queue를 통해 플레이어들을 저장해 둘 예정


@Component
public class MatchingQueue {
    private static final Queue<Map.Entry<String, WebSocketSession>> queueing = new ConcurrentLinkedQueue<>();

    public static void addPlayer(String userId, WebSocketSession webSocketSession){
        queueing.offer(new AbstractMap.SimpleEntry<>(userId,webSocketSession));
    }

    public static Map.Entry<String, WebSocketSession> getPlayer(){
        return queueing.poll();
    }

    public static int queueingSize(){
        return queueing.size();
    }


}
