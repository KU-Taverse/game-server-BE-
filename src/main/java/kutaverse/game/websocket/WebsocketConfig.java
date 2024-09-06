package kutaverse.game.websocket;

import kutaverse.game.chat.repository.ChatRepository;
import kutaverse.game.chat.service.ChatService;
import kutaverse.game.websocket.chat.ChatWebSocketHandler;
import kutaverse.game.websocket.map.MapWebSocketHandler;
import kutaverse.game.websocket.minigame.MiniGameWebsocketHandler;
import kutaverse.game.websocket.taggame.TagGameWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import reactor.core.publisher.Sinks;

import java.util.Map;

@Configuration
public class WebsocketConfig {

	@Bean
	public MapWebSocketHandler mapWebSocketHandler(Sinks.Many<String> sink) {
		return new MapWebSocketHandler(sink);
	}

	@Bean
	public MiniGameWebsocketHandler miniGameWebsocketHandler() {
		return new MiniGameWebsocketHandler();
	}

	@Bean
	public ChatWebSocketHandler chatWebSocketHandler(ChatService chatService) {
		return new ChatWebSocketHandler(chatService);
	}

	@Bean
	public TagGameWebSocketHandler tagGameWebSocketHandler() {
		return new TagGameWebSocketHandler();
	}

	@Bean
	public ChatService chatService(ChatRepository chatRepository) {
		return new ChatService(chatRepository);
	}

	@Bean
	public SimpleUrlHandlerMapping handlerMapping(MapWebSocketHandler mapWsh,
												  MiniGameWebsocketHandler miniWsh,
												  ChatWebSocketHandler chatWsh,
												  TagGameWebSocketHandler tagGameWsh) {
		return new SimpleUrlHandlerMapping(Map.of(
				"/map", mapWsh,
				"/game", miniWsh,
				"/chat", chatWsh,
				"/taggame", tagGameWsh
		), 1);
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