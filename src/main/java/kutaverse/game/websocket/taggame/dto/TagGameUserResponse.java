package kutaverse.game.websocket.taggame.dto;
import kutaverse.game.map.domain.Status;
import kutaverse.game.map.domain.User;
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

    /*public UserResponseDto(User user) {
        this.userId = user.getUserId();
        this.positionX = user.getPositionX();
        this.positionY = user.getPositionY();
        this.positionZ = user.getPositionZ();
        this.rotationPitch = user.getRotationPitch();
        this.rotationYaw = user.getRotationYaw();
        this.rotationRoll = user.getRotationRoll();
<<<<<<< HEAD
        this.status = user.getStatus();
        this.velocityX=user.getVelocityX();
        this.velocityY=user.getVelocityY();
        this.velocityZ=user.getVelocityZ();
=======
        this.status = String.valueOf(user.getStatus());
    }*/

    public static TagGameUserResponse toDto(TagGameUser tagGameUser){
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