package kutaverse.game.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="dis-game-service")
public interface GameRoomClient1 extends GameRoomClient {}
