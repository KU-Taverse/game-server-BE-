package kutaverse.game.map.domain;

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

    private String name;

    private Integer positionX;

    private Integer positionY;


    public User(String key, String name) {
        this.key=key;
        this.name = name;
        positionX=0;
        positionY=0;

    }
}