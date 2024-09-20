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

    private Role role;

    private Double velocityX;

    private Double velocityY;

    private Double velocityZ;

    public void confirmTagger() {
        this.role = Role.TAGGER;
    }

    public static TagGameUser initTagGameUser(String userId, Role role) {
        return TagGameUser.builder()
                .userId(userId)
                .role(role)
                .build();
    }


    public TagGameUser update(TagGameUser tagGameUser) {
        this.positionX = tagGameUser.getPositionX();
        this.positionY = tagGameUser.getPositionY();
        this.positionZ = tagGameUser.getPositionZ();
        this.rotationPitch = tagGameUser.getRotationPitch();
        this.rotationYaw = tagGameUser.getRotationYaw();
        this.rotationRoll = tagGameUser.getRotationRoll();
        this.status = tagGameUser.getStatus();
        this.velocityX = tagGameUser.getVelocityX();
        this.velocityY = tagGameUser.getVelocityY();
        this.velocityZ = tagGameUser.getVelocityZ();
        return this;
    }
}

