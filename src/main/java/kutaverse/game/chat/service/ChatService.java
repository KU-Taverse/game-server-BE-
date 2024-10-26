package kutaverse.game.chat.service;

import kutaverse.game.chat.domain.Chat;
import kutaverse.game.chat.repository.ChatRepository;
import kutaverse.game.websocket.chat.dto.request.ChatRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    public Mono<Chat> save(ChatRequestDto chatRequestDto){
        Chat chat=Chat.builder()
                .senderUserId(chatRequestDto.getUserId())
                .nickname(chatRequestDto.getNickname())
                .content(chatRequestDto.getContent())
                .createdAt(LocalDateTime.now())
                .build();
        return chatRepository.insert(chat)
                .doOnNext(savedChat -> {
                    System.out.println("Saved chat: " + savedChat);
                });

    }
}
