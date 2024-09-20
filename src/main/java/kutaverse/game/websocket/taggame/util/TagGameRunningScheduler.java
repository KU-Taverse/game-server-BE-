package kutaverse.game.websocket.taggame.util;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Component
public class TagGameRunningScheduler {

    /**
     * 멀티게임 이동 함수
     */

    @Scheduled(fixedRate = 33)
    public void sendMessageToClients() {
        TagGameRoomManager.gameRooms.values()
                .forEach(tagGameRoom -> {
                    List<Map.Entry<String, WebSocketSession>> players = tagGameRoom.getPlayers();
                    String message = "플레이중";
                    tagGameRoom.getPlayers().forEach(data -> {
                        WebSocketMessage webSocketMessage = data.getValue().textMessage(message);
                        data.getValue().send(Mono.just(webSocketMessage)).subscribe();
                    });
                });
    }
}
