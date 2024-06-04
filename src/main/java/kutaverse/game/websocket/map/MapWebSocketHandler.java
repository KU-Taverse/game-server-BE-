package kutaverse.game.websocket.map;

import com.fasterxml.jackson.databind.ObjectMapper;
import kutaverse.game.websocket.map.dto.request.UserRequestDto;
import kutaverse.game.websocket.map.handler.WebSocketHandler;
import kutaverse.game.websocket.map.handler.WebSocketHandlerMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;


@Component
@Slf4j
public class MapWebSocketHandler implements org.springframework.web.reactive.socket.WebSocketHandler {

	private final Sinks.Many<String> sink;

	public MapWebSocketHandler(Sinks.Many<String> sink) {
		this.sink = sink;
	}
	public Mono<Void> sendMessageToAllClients(String message) {
		sink.emitNext(message, Sinks.EmitFailureHandler.FAIL_FAST);
		return null;
	}

	@Override
	public Mono<Void> handle(WebSocketSession session) {
		ObjectMapper objectMapper=new ObjectMapper();
		session.receive()
				.map(WebSocketMessage::getPayloadAsText)
				.map(data-> {
					try {
						return objectMapper.readValue(data, UserRequestDto.class);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}


				})
				.subscribe(e->{
					WebSocketHandler webSocketHandler = WebSocketHandlerMapping.getHandler(e);
					webSocketHandler.handle(e);
				});

		return session.send(sink.asFlux().map(session::textMessage));
	}
}
