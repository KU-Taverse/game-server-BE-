package kutaverse.game.map.dto.response;

import kutaverse.game.map.domain.Status;
import kutaverse.game.map.domain.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class GetMapUserResponse {

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
    private int aurora; //오로라
    private int title; //칭호

    public static GetMapUserResponse toDto(User user){
        return builder()
                .userId(user.getUserId())
                .positionX(user.getPositionX())
                .positionY(user.getPositionY())
                .positionZ(user.getPositionZ())
                .rotationPitch(user.getRotationPitch())
                .rotationRoll(user.getRotationRoll())
                .rotationYaw(user.getRotationYaw())
                .velocityX(user.getVelocityX())
                .velocityY(user.getVelocityY())
                .velocityZ(user.getVelocityZ())
                .status(user.getStatus())
                .aurora(user.getAurora())
                .title(user.getTitle())
                .build();
    }
}
