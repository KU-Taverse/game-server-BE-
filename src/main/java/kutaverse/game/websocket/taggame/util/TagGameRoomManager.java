package kutaverse.game.websocket.taggame.util;

import kutaverse.game.websocket.taggame.util.TagGameRoom;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TagGameRoomManager {

    public static final Map<String, TagGameRoom> gameRooms = new ConcurrentHashMap<>();

    public static void addGameRoom(TagGameRoom tagGameRoom) {
        gameRooms.put(tagGameRoom.getRoomId(), tagGameRoom);
    }


}
