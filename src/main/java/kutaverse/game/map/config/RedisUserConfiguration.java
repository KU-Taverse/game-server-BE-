package kutaverse.game.map.config;

import kutaverse.game.map.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;



public class RedisUserConfiguration {

    @Bean
    public <K, V> ReactiveRedisOperations<K, V> redisOperations(ReactiveRedisConnectionFactory factory, RedisSerializer<V> valueSerializer) {
        RedisSerializationContext.RedisSerializationContextBuilder<K, V> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<K, V> context = builder.value(valueSerializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }

    @Bean
    public ReactiveRedisOperations<String, User> redisUserOperations(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<User> userSerializer = new Jackson2JsonRedisSerializer<>(User.class);
        return redisOperations(factory, userSerializer);
    }


}