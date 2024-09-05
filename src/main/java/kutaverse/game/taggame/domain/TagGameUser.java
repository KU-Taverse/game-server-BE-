package kutaverse.game.taggame.domain;

import kutaverse.game.map.domain.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@NoArgsConstructor
public class TagGameUser {

    private String userId;

    private Double positionX;

    private Double positionY;

    private Double positionZ;

    private Double rotationPitch;

    private Double rotationYaw;

    private Double rotationRoll;

    private Status status;

    private Double velocityX;

    private Double velocityY;

    private Double velocityZ;
}

