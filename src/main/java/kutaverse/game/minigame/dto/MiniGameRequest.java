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
    int interrupt;
    List<kutaverse.game.minigame.dto.ActorInfo> ActorInfo;

    public MiniGameRequest() {
    }

    public MiniGameRequest(MiniGameRequestType miniGameRequestType, String roomId, String userId, List<kutaverse.game.minigame.dto.ActorInfo> ActorInfo) {
        this.miniGameRequestType = miniGameRequestType;
        this.roomId = roomId;
        this.userId = userId;
        this.ActorInfo = ActorInfo;
    }
}
