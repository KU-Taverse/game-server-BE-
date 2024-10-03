package kutaverse.game.websocket.minigame.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

import java.util.AbstractMap;
import java.util.ArrayDeque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// Queue를 통해 플레이어들을 저장해 둘 예정


@Component
@Slf4j
public class MatchingQueue {
    private static final ArrayDeque<Map.Entry<String, WebSocketSession>> queueing = new ArrayDeque<>();
    private static final Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();

    public static void addPlayer(String userId, WebSocketSession webSocketSession){
        if(!sessionMap.containsKey(userId)) {
            queueing.offer(new AbstractMap.SimpleEntry<>(userId,webSocketSession));
            sessionMap.put(userId,webSocketSession);
            webSocketSession.send(Mono.just(webSocketSession.textMessage("매칭 대기 중 입니다."))).subscribe();
        }

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
