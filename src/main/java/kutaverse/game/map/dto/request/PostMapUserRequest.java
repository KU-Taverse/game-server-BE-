package kutaverse.game.map.dto.request;

import kutaverse.game.map.domain.Status;
import kutaverse.game.map.domain.User;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class PostMapUserRequest {

    private String userId;
    private Double positionX;
    private Double positionY;
    private Double positionZ;
    private Double rotationPitch;
    private Double rotationYaw;
    private Double rotationRoll;
    private Status status;

    /**
     * service layer에서 엔티티 변환을 위해 필요합니다.
     * @return user
     */
    public User toEntity(){
        return User.builder()
                .userId(userId)
                .positionX(positionX)
                .positionY(positionY)
                .positionZ(positionZ)
                .rotationPitch(rotationPitch)
                .rotationYaw(rotationYaw)
                .rotationRoll(rotationRoll)
                .status(status)
                .build();
    }

    /**
     * 테스트에서 필요합니다.
     * @param user
     * @return PostMapUserRequest
     */
    public static PostMapUserRequest toEntity(User user){
        return PostMapUserRequest.builder()
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
