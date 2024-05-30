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

    public MiniGameRequest() {
    }

    public MiniGameRequest(MiniGameRequestType miniGameRequestType, String roomId, String userId, List<LevelDto> levelDtoList) {
        this.miniGameRequestType = miniGameRequestType;
        this.roomId = roomId;
        this.userId = userId;
        this.levelDtoList = levelDtoList;
    }
}
