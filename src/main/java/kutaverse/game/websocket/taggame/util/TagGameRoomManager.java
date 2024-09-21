package kutaverse.game.websocket.taggame.util;

import kutaverse.game.websocket.taggame.util.TagGameRoom;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TagGameRoomManager {

    public static final Map<String, TagGameRoom> gameRooms = new ConcurrentHashMap<>();

    public static void addGameRoom(TagGameRoom tagGameRoom) {
        gameRooms.put(tagGameRoom.getRoomId(), tagGameRoom);
    }

    public static TagGameRoom getGameRoom(String roomId) { return gameRooms.get(roomId); }

    public static void deleteGameRoom(String roomId) {
        gameRooms.remove(roomId); }


}
