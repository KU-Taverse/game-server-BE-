package kutaverse.game.map.dto.response;

import kutaverse.game.map.domain.Status;
import kutaverse.game.map.domain.User;
import kutaverse.game.map.dto.request.PostMapUserRequest;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PostMapUserResponse {

    private String userId;
    private Double positionX;
    private Double positionY;
    private Double positionZ;
    private Double rotationPitch;
    private Double rotationYaw;
    private Double rotationRoll;
    private Status status;

    public static PostMapUserResponse toDto(User user){
        return builder()
                .userId(user.getUserId())
                .positionX(user.getPositionX())
                .positionY(user.getPositionY())
                .positionZ(user.getPositionZ())
                .rotationPitch(user.getRotationPitch())
                .rotationRoll(user.getRotationRoll())
                .rotationYaw(user.getRotationYaw())
                .status(user.getStatus())
                .build();
    }
}
