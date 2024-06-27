package kutaverse.game.map.config;

import kutaverse.game.map.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableR2dbcRepositories
@EnableR2dbcAuditing
public class MapUserConfiguration {

    @Bean
    public Map<String, User> mapUserMap(){
        return new HashMap<>();
    }
}
