package kutaverse.game.websocket.taggame.util;

import kutaverse.game.taggame.domain.TagGameUser;
import kutaverse.game.taggame.repository.TagGameUserRepository;
import kutaverse.game.taggame.service.TagGameUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketSession;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class TagGameRoomManager {

    private final TagGameUserService tagGameUserService;

    public static final Map<String, TagGameRoom> gameRooms = new ConcurrentHashMap<>();

    public static void addGameRoom(TagGameRoom tagGameRoom) {
        gameRooms.put(tagGameRoom.getRoomId(), tagGameRoom);
    }

    public static TagGameRoom getGameRoom(String roomId) { return gameRooms.get(roomId); }

    public static void deleteGameRoom(String roomId) {
        TagGameRoom tagGameRoom = gameRooms.get(roomId);
        tagGameRoom.delete();
        gameRooms.remove(roomId);
    }

    @Scheduled(fixedRate = 10000)
    public void cleanMemberIfClosed(){
        for (TagGameRoom tagGameRoom : gameRooms.values()) {
            List<Map.Entry<String, WebSocketSession>> players = tagGameRoom.getPlayers();
            for (Map.Entry<String, WebSocketSession> player : players) {
                if(!player.getValue().isOpen()){
                    tagGameUserService.closedUser(player.getKey()).subscribe();
                }
            }
        }
    }


}
