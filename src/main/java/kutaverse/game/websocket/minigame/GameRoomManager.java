package kutaverse.game.websocket.minigame;

import com.fasterxml.jackson.core.JsonProcessingException;
import kutaverse.game.minigame.dto.MiniGameRequest;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
}
