package kutaverse.game.websocket.minigame.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import kutaverse.game.minigame.dto.MiniGameRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;
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

    public static void updateGameRoom(String roomId, MiniGameRequest miniGameRequest){
        GameRoom gameRoom = gameRooms.get(roomId);
        gameRoom.updatePlayerScore(miniGameRequest.getUserId(),miniGameRequest.getScore());
    }

    public static void endGameAndNotifyRoom(MiniGameRequest miniGameRequest) throws JsonProcessingException {
        GameRoom gameRoom = gameRooms.get(miniGameRequest.getRoomId());
        gameRoom.endGameAndNotifyPlayers(miniGameRequest);
    }

    public static void sendPlayerData(String roomId, String userId, MiniGameRequest data) throws JsonProcessingException {
        GameRoom gameRoom = getGameRoom(roomId);
        gameRoom.sendDataToOther(userId,data);
    }

    @Scheduled(fixedDelay = 5000) // 5초마다 실행
    public void checkPlayerStatus() {
        List<String> roomsToRemove = new ArrayList<>();
        for (Map.Entry<String, GameRoom> entry : gameRooms.entrySet()) {
            String roomId = entry.getKey();
            GameRoom gameRoom = entry.getValue();
            boolean playerLeft =false;
            for (Map.Entry<String, WebSocketSession> playerEntry : gameRoom.getPlayers().entrySet()) {
                String userId = playerEntry.getKey();
                WebSocketSession session = playerEntry.getValue();

                if (!session.isOpen()) { // WebSocket 연결이 닫혔는지 확인
                    gameRoom.handlePlayerLeft(userId);
                    playerLeft = true;
                }
            }
            if (playerLeft && gameRoom.getPlayers().isEmpty()) {
                gameRooms.remove(roomId);
            }
        }

    }
}
