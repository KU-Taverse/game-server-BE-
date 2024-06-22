package kutaverse.game.map.config;

import kutaverse.game.map.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MapUserConfiguration {

    @Bean
    public Map<String, User> mapUserMap(){
        return new HashMap<>();
    }
}
