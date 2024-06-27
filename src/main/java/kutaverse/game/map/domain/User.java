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
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Table(name = "MapUser")
public class User {

    @Id
    private String userId;

    private Double positionX;

    private Double positionY;

    private Double positionZ;

    private Double rotationPitch;

    private Double rotationYaw;

    private Double rotationRoll;

    private Status status;

    private Double velocityX;

    private Double velocityY;

    private Double velocityZ;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime localDateTime;

    @Builder
    public User(String userId, Double positionX, Double positionY, Double positionZ,
                Double rotationPitch, Double rotationYaw, Double rotationRoll,
                Double velocityX, Double velocityY, Double velocityZ, Status status) {
        this.userId = userId;
        this.positionX = Math.round(positionX*1000.0)/1000.0;
        this.positionY = Math.round(positionY*1000.0)/1000.0;;
        this.positionZ = Math.round(positionZ*1000.0)/1000.0;
        this.rotationPitch = Math.round(rotationPitch*1000.0)/1000.0;
        this.rotationYaw = Math.round(rotationYaw*1000.0)/1000.0;
        this.rotationRoll = Math.round(rotationRoll*1000.0)/1000.0;
        this.status = status;
        this.velocityX = Math.round(velocityX*1000.0)/1000.0;
        this.velocityY = Math.round(velocityY*1000.0)/1000.0;;
        this.velocityZ = Math.round(velocityZ*1000.0)/1000.0;
        this.localDateTime = LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    }

    public void updateTime() {
        this.localDateTime=LocalDateTime.now();
    }

    public void setStatus(Status status){
        this.status=status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) && Objects.equals(positionX, user.positionX) && Objects.equals(positionY, user.positionY) && Objects.equals(positionZ, user.positionZ) && Objects.equals(rotationPitch, user.rotationPitch) && Objects.equals(rotationYaw, user.rotationYaw) && Objects.equals(rotationRoll, user.rotationRoll) && status == user.status && Objects.equals(localDateTime, user.localDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, positionX, positionY, positionZ, rotationPitch, rotationYaw, rotationRoll, status, localDateTime);
    }
}