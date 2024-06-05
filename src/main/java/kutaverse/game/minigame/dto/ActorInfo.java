package kutaverse.game.minigame.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ActorInfo {
    private int level;
    private int x;
    private int z;

    public ActorInfo() {
    }


    public ActorInfo(int level, int x, int z) {
        this.level = level;
        this.x = x;
        this.z = z;
    }

}
