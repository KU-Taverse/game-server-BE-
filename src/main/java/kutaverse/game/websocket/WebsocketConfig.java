package kutaverse.game.websocket;

import kutaverse.game.websocket.map.MapWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import reactor.core.publisher.Sinks;

import java.util.Map;

@Configuration
public class WebsocketConfig {

	@Bean
	public SimpleUrlHandlerMapping handlerMapping(MapWebSocketHandler wsh) {
		return new SimpleUrlHandlerMapping(Map.of("/map", wsh), 1);
	}

	@Bean
	public WebSocketHandlerAdapter webSocketHandlerAdapter() {
		return new WebSocketHandlerAdapter();
	}

	@Bean
	public Sinks.Many<String> sink() {
		return Sinks.many().multicast().directBestEffort();
	}

}