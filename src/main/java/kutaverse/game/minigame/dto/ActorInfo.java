package kutaverse.game.minigame.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ActorInfo {
    private String level;
    private double x;
    private double z;

    public ActorInfo() {
    }


    public ActorInfo(String level, double x, double z) {
        this.level = level;
        this.x = x;
        this.z = z;
    }

}
