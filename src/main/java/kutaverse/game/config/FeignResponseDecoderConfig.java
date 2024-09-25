package kutaverse.game.config;

import feign.codec.Decoder;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class FeignResponseDecoderConfig {

    @Bean
    public Decoder feignDecoder() {
        // RestTemplate을 사용하여 HttpMessageConverter를 가져옴
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();

        // SpringDecoder를 사용할 때 HttpMessageConverters를 전달
        return new SpringDecoder(() -> new HttpMessageConverters(messageConverters));
    }
}
