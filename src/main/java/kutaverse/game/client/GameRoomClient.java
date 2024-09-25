package kutaverse.game.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

@FeignClient(name="dis-game-service")
public interface GameRoomClient {
    @GetMapping("/actuator/health")
    String healthCheck();
}
