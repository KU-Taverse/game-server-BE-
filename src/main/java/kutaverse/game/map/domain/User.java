package kutaverse.game.map.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Getter
@RedisHash(value = "user", timeToLive = 30) //초단위
@NoArgsConstructor
@ToString
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

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime localDateTime;

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
        this.localDateTime=LocalDateTime.now();
    }

    public void updateTime() {
        this.localDateTime=LocalDateTime.now();
    }
}