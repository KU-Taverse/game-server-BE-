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

    private int userObjectNumber;

    private Double positionX;

    private Double positionY;

    private Double positionZ;

    private Double rotationPitch;

    private Double rotationYaw;

    private Double rotationRoll;

    @Builder.Default
    private LifeStatus lifeStatus = LifeStatus.NOT_TAGGED;

    private Role role;

    public void confirmTagged() {
        this.lifeStatus = LifeStatus.TAGGED;
    }

    public void confirmTaggerOut() {
        this.lifeStatus = LifeStatus.TAGGER_OUT;
    }

    public static TagGameUser initTagGameUser(String userId, Role role, int userObjectNumber) {
        return TagGameUser.builder()
                .userId(userId)
                .role(role)
                .userObjectNumber(userObjectNumber)
                .build();
    }


    public TagGameUser update(TagGameUser tagGameUser) {
        this.positionX = tagGameUser.getPositionX();
        this.positionY = tagGameUser.getPositionY();
        this.positionZ = tagGameUser.getPositionZ();
        this.rotationPitch = tagGameUser.getRotationPitch();
        this.rotationYaw = tagGameUser.getRotationYaw();
        this.rotationRoll = tagGameUser.getRotationRoll();
        return this;
    }
}

