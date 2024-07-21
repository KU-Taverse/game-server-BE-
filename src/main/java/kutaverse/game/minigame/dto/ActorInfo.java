package kutaverse.game.minigame.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ActorInfo {
    private String level;
    private float x;
    private float z;
    private float yaw;

    public ActorInfo() {
    }


    public ActorInfo(String level, float x, float z, float yaw) {
        this.level = level;
        this.x = x;
        this.z = z;
        this.yaw = yaw;
    }

}
