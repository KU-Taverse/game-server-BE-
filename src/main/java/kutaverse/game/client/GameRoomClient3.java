package kutaverse.game.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="dis-game-service-3")
public interface GameRoomClient3 extends GameRoomClient{}
