package kutaverse.game.map.dto;

import kutaverse.game.map.domain.Status;
import kutaverse.game.map.domain.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserRequestDto {

    private String userId;
    private String positionX;
    private String positionY;
    private String positionZ;
    private String eulerAngleX;
    private String eulerAngleY;
    private String eulerAngleZ;
    private String status;

    public User toEntity(){
        return User.builder()
                .key(userId)
                .positionX(Integer.valueOf(positionX))
                .positionY(Integer.valueOf(positionY))
                .positionZ(Integer.valueOf(positionZ))
                .eulerAngleX(Integer.valueOf(eulerAngleX))
                .eulerAngleY(Integer.valueOf(eulerAngleY))
                .eulerAngleZ(Integer.valueOf(eulerAngleZ))
                .status(Status.JUMP)
                .build();
    }
}
