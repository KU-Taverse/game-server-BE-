package kutaverse.game.websocket.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import kutaverse.game.chat.service.ChatService;
import kutaverse.game.websocket.chat.dto.request.ChatRequestDto;
import kutaverse.game.websocket.chat.dto.response.ChatResponseDto;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatWebSocketHandler implements WebSocketHandler {

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    private final ChatService chatService;
    public ChatWebSocketHandler(ChatService chatService){
        this.chatService=chatService;
    }

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        sessions.put(session.getId(), session);
        ObjectMapper objectMapper=new ObjectMapper();
        session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .map(data-> {
                    try {
                        return objectMapper.readValue(data, ChatRequestDto.class);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .doOnNext(System.out::println)
                .map(chatRequest -> {
                    chatService.save(chatRequest).subscribe();
                    ChatResponseDto response = new ChatResponseDto(chatRequest.getUserId(), chatRequest.getNickname(), chatRequest.getContent());
                    try {
                        return objectMapper.writeValueAsString(response);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .flatMap(data->{
                    Flux.fromIterable(sessions.values())
                            .flatMap(wsSession -> {
                                if (!wsSession.getId().equals(session.getId())) {
                                    return wsSession.send(Mono.just(wsSession.textMessage(data)));
                                } else {
                                    return Mono.empty();
                                }
                            }).subscribe();
                    return Mono.never();
                })
                .subscribe();

        return Mono.never();
    }
}
