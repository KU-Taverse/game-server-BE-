package kutaverse.game.map.websocket;

import kutaverse.game.map.websocket.handler.WebSocketHandler;
import kutaverse.game.map.websocket.handler.WebSocketHandlerMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;


@Component
@Slf4j
public class CustomWebSocketHandler implements org.springframework.web.reactive.socket.WebSocketHandler {

	private final Sinks.Many<String> sink;

	public CustomWebSocketHandler(Sinks.Many<String> sink) {
		this.sink = sink;
	}
	public void sendMessageToAllClients(String message) {
		sink.emitNext(message, Sinks.EmitFailureHandler.FAIL_FAST);
	}

	@Override
	public Mono<Void> handle(WebSocketSession session) {
		var output = session.receive()
				// 메시지를 JSON 객체로 변환
				.map(e -> e.getPayloadAsText())
				.map(e -> {
					WebSocketHandler webSocketHandler= WebSocketHandlerMapping.getHandler(e);

					return webSocketHandler.handle(e);
				});

		output.subscribe(s -> sink.emitNext(s, Sinks.EmitFailureHandler.FAIL_FAST));

		return session.send(sink.asFlux().map(session::textMessage));
	}
}
