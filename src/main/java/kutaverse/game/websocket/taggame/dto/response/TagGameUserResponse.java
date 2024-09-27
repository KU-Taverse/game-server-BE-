package kutaverse.game.websocket.taggame.dto.response;
import kutaverse.game.map.domain.Status;
import kutaverse.game.taggame.domain.LifeStatus;
import kutaverse.game.taggame.domain.TagGameUser;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TagGameUserResponse {

    private String userId;
    private int userObjectNumber;
    private Double positionX;
    private Double positionY;
    private Double positionZ;
    private Double rotationPitch;
    private Double rotationYaw;
    private Double rotationRoll;
    private LifeStatus lifeStatus;

    public static TagGameUserResponse toDto(TagGameUser tagGameUser){
        return builder()
                .userId(tagGameUser.getUserId())
                .userObjectNumber(tagGameUser.getUserObjectNumber())
                .positionX(tagGameUser.getPositionX())
                .positionY(tagGameUser.getPositionY())
                .positionZ(tagGameUser.getPositionZ())
                .rotationPitch(tagGameUser.getRotationPitch())
                .rotationRoll(tagGameUser.getRotationRoll())
                .rotationYaw(tagGameUser.getRotationYaw())
                .lifeStatus(tagGameUser.getLifeStatus())
                .build();
    }
}