package kutaverse.game.websocket.minigame;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import kutaverse.game.client.GameRoomClient;
import kutaverse.game.client.GameRoomClient1;
import kutaverse.game.client.GameRoomClient2;
import kutaverse.game.client.GameRoomClient3;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class RoundRobinAllocator {
    private final List<String> roomServers;
    private final List<GameRoomClient> roomClients;
    private final ObjectMapper objectMapper;
    private int currentIndex = 0;

    public RoundRobinAllocator(List<String> roomServers, GameRoomClient1 client1, GameRoomClient2 client2,
                               GameRoomClient3 client3, ObjectMapper objectMapper) {
        this.roomClients = Arrays.asList(client1, client2, client3);
        this.roomServers = roomServers;
        this.objectMapper = objectMapper;
    }

    public synchronized String getRoomServer() throws JsonProcessingException {
        int initialIndex = currentIndex;
        String status;

        do {
            String server = roomServers.get(currentIndex);
            GameRoomClient currentClient = roomClients.get(currentIndex);
            try {

                String response = currentClient.healthCheck();
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

