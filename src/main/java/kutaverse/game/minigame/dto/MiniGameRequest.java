package kutaverse.game.minigame.dto;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class MiniGameRequest {
    MiniGameRequestType miniGameRequestType;
    String roomId;
    String userId;
    List<LevelDto> levelDtoList;

}
