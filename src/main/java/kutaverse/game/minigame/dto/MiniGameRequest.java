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
    List<ActorInfo> actorInfo;

    public MiniGameRequest() {
    }

    public MiniGameRequest(MiniGameRequestType miniGameRequestType, String roomId, String userId, int interrupt, List<ActorInfo> actorInfo) {
        this.miniGameRequestType = miniGameRequestType;
        this.roomId = roomId;
        this.userId = userId;
        this.interrupt = interrupt;
        this.actorInfo = actorInfo;
    }
}
