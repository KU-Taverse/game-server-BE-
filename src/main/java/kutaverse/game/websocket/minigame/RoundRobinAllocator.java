package kutaverse.game.websocket.minigame;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kutaverse.game.client.GameRoomClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoundRobinAllocator {
    private final List<String> roomServers;
    private final GameRoomClient gameRoomClient;
    private final ObjectMapper objectMapper;
    private int currentIndex = 0;

    public RoundRobinAllocator(List<String> roomServers, GameRoomClient gameRoomClient, ObjectMapper objectMapper) {
        this.gameRoomClient = gameRoomClient;
        this.roomServers = roomServers;
        this.objectMapper = objectMapper;
    }

    public synchronized String getRoomServer() throws JsonProcessingException {
        int initialIndex = currentIndex;
        String status;

        do {
            String server = roomServers.get(currentIndex);
            try {
                String response = gameRoomClient.healthCheck();
                JsonNode root = objectMapper.readTree(response);
                status = root.get("status").asText();

                if ("UP".equals(status)) {
                    currentIndex = (currentIndex + 1) % roomServers.size();
                    return server;
                }

            } catch (Exception e) {
                System.err.println("서버 상태 문제: " + e.getMessage());
            }

            currentIndex = (currentIndex + 1) % roomServers.size();

        } while (currentIndex != initialIndex);

        throw new IllegalStateException("모든 서버 다운.");
    }
}

