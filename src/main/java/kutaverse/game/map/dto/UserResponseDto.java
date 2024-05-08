package kutaverse.game.map.dto;

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
    private String status;

    public UserResponseDto(User user) {
        this.userId = user.getKey();
        this.positionX=user.getPositionX();
        this.positionY=user.getPositionY();
        this.positionZ=user.getPositionZ();
        this.rotationPitch=user.getRotationPitch();
        this.rotationYaw=user.getRotationYaw();
        this.rotationRoll=user.getRotationRoll();
        this.status = String.valueOf(user.getStatus());
    }

    @Override
    public String toString() {
        return "{" +
                "userId='" + userId + '\'' +
                ", positionX='" + positionX + '\'' +
                ", positionY='" + positionY + '\'' +
                ", positionZ='" + positionZ + '\'' +
                ", ='" + rotationPitch + '\'' +
                ", rotationYaw='" + rotationYaw + '\'' +
                ", rotationRoll='" + rotationRoll + '\'' +
                ", status='" + status + '\'' +
                '}';
    }


}
