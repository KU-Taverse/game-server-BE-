package kutaverse.game.minigame.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PositionDto {
    private int x;
    private int z;
    private int rotation;

    public PositionDto() {
    }

    public PositionDto(int x, int z,int rotation) {
        this.x = x;
        this.z = z;
        this.rotation = rotation;
    }
}
