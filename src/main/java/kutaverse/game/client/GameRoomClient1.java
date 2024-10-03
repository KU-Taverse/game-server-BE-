package kutaverse.game.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="dis-game-service-1")
public interface GameRoomClient1 extends GameRoomClient {}
