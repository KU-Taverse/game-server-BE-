package kutaverse.game.websocket.taggame.dto.request;
import kutaverse.game.map.domain.Status;
import kutaverse.game.taggame.domain.TagGameUser;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
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

    public static TagGameUser toEntity(TagGameUserRequest tagGameUserRequest){
        return TagGameUser.builder()
                .userId(tagGameUserRequest.getUserId())
                .positionX(Math.round(tagGameUserRequest.getPositionX()*1000.0)/1000.0)
                .positionY(Math.round(tagGameUserRequest.getPositionY()*1000.0)/1000.0)
                .positionZ(Math.round(tagGameUserRequest.getPositionZ()*1000.0)/1000.0)
                .rotationPitch(Math.round(tagGameUserRequest.getRotationPitch()*1000.0)/1000.0)
                .rotationRoll(Math.round(tagGameUserRequest.getRotationRoll()*1000.0)/1000.0)
                .rotationYaw(Math.round(tagGameUserRequest.getRotationYaw()*1000.0)/1000.0)
                .status(tagGameUserRequest.getStatus())
                .velocityX(Math.round(tagGameUserRequest.getVelocityX()*1000.0)/1000.0)
                .velocityY(Math.round(tagGameUserRequest.getVelocityY()*1000.0)/1000.0)
                .velocityZ(Math.round(tagGameUserRequest.getVelocityZ()*1000.0)/1000.0)
                .build();
    }
}