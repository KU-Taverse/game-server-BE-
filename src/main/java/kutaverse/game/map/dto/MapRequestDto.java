package kutaverse.game.map.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class MapRequestDto {

    private String userId;
    private String positionX;
    private String positionY;
    private String positionZ;
    private String eulerAngleX;
    private String eulerAngleY;
    private String eulerAngleZ;
    private String status;


}
