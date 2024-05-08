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

    private Double positionX;

    private Double positionY;

    private Double positionZ;

    private Double rotationPitch;

    private Double rotationYaw;

    private Double rotationRoll;

    private Status status;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime localDateTime;

    @Builder
    public User(String key, Double positionX, Double positionY, Double positionZ, Double rotationPitch, Double rotationYaw, Double rotationRoll, Status status) {
        this.key = key;
        this.positionX = Math.round(positionX*1000.0)/1000.0;
        this.positionY = Math.round(positionY*1000.0)/1000.0;;
        this.positionZ = Math.round(positionZ*1000.0)/1000.0;
        this.rotationPitch = Math.round(rotationPitch*1000.0)/1000.0;
        this.rotationYaw = Math.round(rotationYaw*1000.0)/1000.0;
        this.rotationRoll = Math.round(rotationRoll*1000.0)/1000.0;
        this.status = status;
        this.localDateTime=LocalDateTime.now();
    }

    public void updateTime() {
        this.localDateTime=LocalDateTime.now();
    }

    public void setStatus(Status status){
        this.status=status;
    }
}