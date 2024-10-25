package kutaverse.game.websocket.minigame;

import com.fasterxml.jackson.databind.ObjectMapper;
import kutaverse.game.client.GameRoomClient;
import kutaverse.game.client.GameRoomClient1;
import kutaverse.game.client.GameRoomClient2;
import kutaverse.game.client.GameRoomClient3;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class AllocatorConfig {

    @Bean
    public RoundRobinAllocator roundRobinAllocator(GameRoomClient1 client1, GameRoomClient2 client2,
                                                   GameRoomClient3 client3, ObjectMapper objectMapper) {
        // 서버 주소 리스트를 직접 정의
        // 나중에 서버 도메인 주소로 바꾸어야함
        List<String> roomServers = Arrays.asList(
                "wss://kutaverse.xyz/dis-game-service-1/game",
                "wss://kutaverse.xyz/dis-game-service-2/game",
                "wss://kutaverse.xyz/dis-game-service-3/game"
        );
        return new RoundRobinAllocator(roomServers, client1, client2, client3, objectMapper);
    }
}
