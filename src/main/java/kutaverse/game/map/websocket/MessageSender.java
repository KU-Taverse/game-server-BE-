package kutaverse.game.map.websocket;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {

    private final CustomWebSocketHandler webSocketHandler;

    public MessageSender(CustomWebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

    @Scheduled(fixedRate = 5000) // 5초마다 실행
    public void sendMessageToClients() {
        String message = "\\{\"userId\":\"감자\",\"positionX\":\"1\",\"positionY\":\"2\",\"positionZ\":\"3\",\"eulerAngleX\":\"4\",\"eulerAngleY\":\"5\",\"eulerAngleZ\":\"6\",\"status\":\"7\"\\}";
        webSocketHandler.sendMessageToAllClients(message);
    }
}