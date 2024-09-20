package kutaverse.game.websocket.taggame.dto.request;
import kutaverse.game.map.domain.Status;
import kutaverse.game.taggame.domain.TagGameUser;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TagGameUserRequest {

    private String userId;
    private Double positionX;
    private Double positionY;
    private Double positionZ;
    private Double rotationPitch;
    private Double rotationYaw;
    private Double rotationRoll;
    private Double velocityX;
    private Double velocityY;
    private Double velocityZ;
    private Status status;

    public static TagGameUserRequest toDto(TagGameUser tagGameUser){
        return builder()
                .userId(tagGameUser.getUserId())
                .positionX(tagGameUser.getPositionX())
                .positionY(tagGameUser.getPositionY())
                .positionZ(tagGameUser.getPositionZ())
                .rotationPitch(tagGameUser.getRotationPitch())
                .rotationRoll(tagGameUser.getRotationRoll())
                .rotationYaw(tagGameUser.getRotationYaw())
                .velocityX(tagGameUser.getVelocityX())
                .velocityY(tagGameUser.getVelocityY())
                .velocityZ(tagGameUser.getVelocityZ())
                .status(tagGameUser.getStatus())
                .build();
    }
}