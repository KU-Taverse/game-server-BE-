package kutaverse.game.minigame.dto;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class MiniGameRequest {
    String roomId;
    String userId;
    List<LevelDto> levelDtoList;

}
