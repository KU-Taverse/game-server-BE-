package kutaverse.game.websocket.minigame;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class RoomService {

    private RoundRobinAllocator roundRobinAllocator;

    public RoomService() {
        List<String> roomServers = Arrays.asList("ws://localhost:9001", "ws://localhost:9002", "ws://localhost:9003");
        this.roundRobinAllocator = new RoundRobinAllocator(roomServers);
    }

    public String assignRoom(String userId) {
        String assignedRoomServer = roundRobinAllocator.getRoomServer();
        System.out.println("할당된 서버의 주소: " + assignedRoomServer);
        return assignedRoomServer;
    }
}
