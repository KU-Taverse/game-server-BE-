package kutaverse.game.minigame.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
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
