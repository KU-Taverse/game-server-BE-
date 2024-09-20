package kutaverse.game.taggame.domain;

import kutaverse.game.map.domain.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @Builder
    public TagGameUser(String userId, Double positionX, Double positionY, Double positionZ,
                Double rotationPitch, Double rotationYaw, Double rotationRoll,
                Double velocityX, Double velocityY, Double velocityZ, Status status) {
        this.userId = userId;
        this.positionX = Math.round(positionX*1000.0)/1000.0;
        this.positionY = Math.round(positionY*1000.0)/1000.0;;
        this.positionZ = Math.round(positionZ*1000.0)/1000.0;
        this.rotationPitch = Math.round(rotationPitch*1000.0)/1000.0;
        this.rotationYaw = Math.round(rotationYaw*1000.0)/1000.0;
        this.rotationRoll = Math.round(rotationRoll*1000.0)/1000.0;
        this.status = status;
        this.velocityX = Math.round(velocityX*1000.0)/1000.0;
        this.velocityY = Math.round(velocityY*1000.0)/1000.0;;
        this.velocityZ = Math.round(velocityZ*1000.0)/1000.0;
    }
}

