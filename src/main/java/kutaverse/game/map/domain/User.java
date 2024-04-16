package kutaverse.game.map.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "user", timeToLive = 30) //초단위
@NoArgsConstructor
public class User {

    @Id
    private String key;

    private Integer positionX;

    private Integer positionY;

    private Integer positionZ;

    private Integer eulerAngleX;

    private Integer eulerAngleY;

    private Integer eulerAngleZ;

    private Status status;

    @Builder
    public User(String key, Integer positionX, Integer positionY, Integer positionZ, Integer eulerAngleX, Integer eulerAngleY, Integer eulerAngleZ, Status status) {
        this.key = key;
        this.positionX = positionX;
        this.positionY = positionY;
        this.positionZ = positionZ;
        this.eulerAngleX = eulerAngleX;
        this.eulerAngleY = eulerAngleY;
        this.eulerAngleZ = eulerAngleZ;
        this.status = status;
    }

}