package kutaverse.game.taggame.config;

import kutaverse.game.map.domain.User;
import kutaverse.game.taggame.domain.TagGameUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class TagGameUserConfiguration {

    @Bean
    public Map<String, TagGameUser> TagGameUserMap(){
        return new ConcurrentHashMap<>();
    }
}
