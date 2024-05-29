package kutaverse.game.minigame.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class LevelDto {
    private int level;
    private List<PositionDto> positions;

    public LevelDto() {
    }

    public LevelDto(int level, List<PositionDto> positions) {
        this.level = level;
        this.positions = positions;
    }
}
