package kutaverse.game.websocket.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import kutaverse.game.chat.domain.Chat;
import kutaverse.game.chat.service.ChatService;
import kutaverse.game.websocket.chat.dto.request.ChatRequestDto;
import kutaverse.game.websocket.chat.dto.response.ChatResponseDto;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

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
                .flatMap(chatRequest -> {
                    //chatService.save(chatRequest).subscribe();
                    return chatService.save(chatRequest).map(savedChat -> {
                        // 저장된 결과를 기반으로 응답 생성
                        System.out.println(savedChat.getId());
                        ChatResponseDto response = new ChatResponseDto(savedChat.getSenderUserId(), savedChat.getNickname(), savedChat.getContent());
                        try {
                            return objectMapper.writeValueAsString(response);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
                })
                .flatMap(data->{
                    return Flux.fromIterable(sessions.entrySet())
                            .flatMap(entry -> {
                                WebSocketSession wsSession = entry.getValue();
                                String key = entry.getKey();
                                if(!wsSession.isOpen()) {
                                    wsSession.close().subscribe();
                                    sessions.remove(key);
                                    return Mono.never();
                                }
                                else
                                    return wsSession.send(Mono.just(wsSession.textMessage(data)));
                            }).then();
                    //return Mono.never();
                })
                .subscribe();

        return Mono.never();
    }
}
