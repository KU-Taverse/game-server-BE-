package kutaverse.game.map.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kutaverse.game.map.dto.MapRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;


@Component
@Slf4j
public class CustomWebSocketHandler implements WebSocketHandler {

	private final Sinks.Many<String> sink;

	public CustomWebSocketHandler(Sinks.Many<String> sink) {
		this.sink = sink;
	}

	@Override
	public Mono<Void> handle(WebSocketSession session) {
		var output = session.receive()
				// 메시지를 JSON 객체로 변환
				.map(e -> e.getPayloadAsText())
				.map(e -> {
					try {
						// 메시지를 파싱
						ObjectMapper objectMapper=new ObjectMapper();
						MapRequestDto mapRequestDto=objectMapper.readValue(e,MapRequestDto.class);
						return mapRequestDto.toString();
					} catch (JsonMappingException ex) {
						throw new RuntimeException("jsonMappring Exception 발생");
					} catch (JsonProcessingException ex) {
						throw new RuntimeException("jsonprocessingexception 발생");
					}
				});

		output.subscribe(s -> sink.emitNext(s, Sinks.EmitFailureHandler.FAIL_FAST));

		return session.send(sink.asFlux().map(session::textMessage));
	}
}
