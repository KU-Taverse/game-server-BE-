package kutaverse.game.websocket.minigame;

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
}
