package kutaverse.game.minigame.dto;

import lombok.Getter;

@Getter
public class PositionDto {
    private int x;
    private int z;
    private int rotation;

    public PositionDto(int x, int z, int rotation) {
        this.x = x;
        this.z = z;
        this.rotation = rotation;
    }
}
