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
    private String positionX;
    private String positionY;
    private String positionZ;
    private String eulerAngleX;
    private String eulerAngleY;
    private String eulerAngleZ;
    private String status;

    public UserResponseDto(User user) {
        this.userId = user.getKey();
        this.positionX = String.valueOf(user.getPositionX());
        this.positionY = String.valueOf(user.getPositionY());
        this.positionZ = String.valueOf(user.getPositionZ());
        this.eulerAngleX = String.valueOf(user.getEulerAngleX());
        this.eulerAngleY = String.valueOf(user.getEulerAngleY());
        this.eulerAngleZ = String.valueOf(user.getEulerAngleZ());
        this.status = String.valueOf(user.getStatus());
    }

    @Override
    public String toString() {
        return "{" +
                "userId='" + userId + '\'' +
                ", positionX='" + positionX + '\'' +
                ", positionY='" + positionY + '\'' +
                ", positionZ='" + positionZ + '\'' +
                ", eulerAngleX='" + eulerAngleX + '\'' +
                ", eulerAngleY='" + eulerAngleY + '\'' +
                ", eulerAngleZ='" + eulerAngleZ + '\'' +
                ", status='" + status + '\'' +
                '}';
    }


}
