package kutaverse.game.websocket.minigame;

import com.fasterxml.jackson.databind.ObjectMapper;
import kutaverse.game.client.GameRoomClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class AllocatorConfig {

    @Bean
    public RoundRobinAllocator roundRobinAllocator(GameRoomClient gameRoomClient, ObjectMapper objectMapper) {
        // 서버 주소 리스트를 직접 정의
        List<String> roomServers = Arrays.asList(
                "ws://localhost:9001/game-service/game",
                "ws://localhost:9002/game-service/game",
                "ws://localhost:9003/game-service/game"
        );
        return new RoundRobinAllocator(roomServers, gameRoomClient, objectMapper);
    }
}
