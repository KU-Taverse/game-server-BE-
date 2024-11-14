package kutaverse.game.websocket.map.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import kutaverse.game.map.domain.Status;
import kutaverse.game.map.domain.User;
import kutaverse.game.map.domain.MapRequestType;
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
    private Double rotationPitch;
    private Double rotationYaw;
    private Double rotationRoll;
    private Double velocityX;
    private Double velocityY;
    private Double velocityZ;
    private Status status;
    private int aurora; //오로라

    @JsonProperty("title_background")
    private int titleBackground; //배경

    @JsonProperty("title_color")
    private int titleColor; //이름

    public User toEntity(){
        return User.builder()
                .userId(userId)
                .positionX(positionX)
                .positionY(positionY)
                .positionZ(positionZ)
                .rotationPitch(rotationPitch)
                .rotationYaw(rotationYaw)
                .rotationRoll(rotationRoll)
                .velocityX(velocityX)
                .velocityY(velocityZ)
                .velocityZ(velocityZ)
                .status(status)
                .aurora(aurora)
                .title_background(titleBackground)
                .title_color(titleColor)
                .build();
    }
}
