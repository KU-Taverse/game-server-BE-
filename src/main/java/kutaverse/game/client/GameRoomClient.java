package kutaverse.game.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


public interface GameRoomClient {
    @GetMapping("/actuator/health")
    String healthCheck();
}
