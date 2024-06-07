package kutaverse.game.websocket.map.dto.response;

import kutaverse.game.map.domain.Status;
import kutaverse.game.map.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
public class UserResponseDto {

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

    public UserResponseDto(User user) {
        this.userId = user.getUserId();
        this.positionX = user.getPositionX();
        this.positionY = user.getPositionY();
        this.positionZ = user.getPositionZ();
        this.rotationPitch = user.getRotationPitch();
        this.rotationYaw = user.getRotationYaw();
        this.rotationRoll = user.getRotationRoll();
        this.status = user.getStatus();
        this.velocityX=user.getVelocityX();
        this.velocityY=user.getVelocityY();
        this.velocityZ=user.getVelocityZ();
    }
}
