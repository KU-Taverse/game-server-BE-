package kutaverse.game.websocket.minigame;

import com.fasterxml.jackson.core.JsonProcessingException;
import kutaverse.game.minigame.dto.MiniGameRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GameRoomManager {
    public static final Map<String, GameRoom> gameRooms = new ConcurrentHashMap<>();

    public static void addGameRoom(GameRoom gameRoom) {
        gameRooms.put(gameRoom.getRoomId(), gameRoom);
    }

    public static GameRoom getGameRoom(String roomId) {
        return gameRooms.get(roomId);
    }

    public static void removeGameRoom(String roomId) {
        gameRooms.remove(roomId);
    }

    public static void sendPlayerData(String roomId, String userId, MiniGameRequest data) throws JsonProcessingException {
        GameRoom gameRoom = getGameRoom(roomId);
        gameRoom.sendDataToOther(userId,data);
    }

    @Scheduled(fixedDelay = 5000) // 5초마다 실행
    public void checkPlayerStatus() {
        for (Map.Entry<String, GameRoom> entry : gameRooms.entrySet()) {
            GameRoom gameRoom = entry.getValue();
            for (Map.Entry<String, WebSocketSession> playerEntry : gameRoom.getPlayers().entrySet()) {
                String userId = playerEntry.getKey();
                WebSocketSession session = playerEntry.getValue();
                if (!session.isOpen()) { // WebSocket 연결이 닫혔는지 확인
                    gameRoom.handlePlayerLeft(userId);
                }
            }
        }
    }
}
