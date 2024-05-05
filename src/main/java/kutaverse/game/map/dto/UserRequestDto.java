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
    private MapRequestType mapRequestType;
    private String userId;
    private Double positionX;
    private Double positionY;
    private Double positionZ;
    private Double eulerAngleX;
    private Double eulerAngleY;
    private Double eulerAngleZ;
    private Status status;

    public User toEntity(){
        return User.builder()
                .key(userId)
                .positionX(positionX)
                .positionY(positionY)
                .positionZ(positionZ)
                .eulerAngleX(eulerAngleX)
                .eulerAngleY(eulerAngleY)
                .eulerAngleZ(eulerAngleZ)
                .status(status)
                .build();
    }
}
