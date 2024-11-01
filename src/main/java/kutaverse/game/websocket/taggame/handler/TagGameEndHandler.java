package kutaverse.game.websocket.taggame.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kutaverse.game.taggame.domain.Role;
import kutaverse.game.taggame.repository.TagGameUserRepository;
import kutaverse.game.taggame.service.TagGameUserService;
import kutaverse.game.websocket.taggame.dto.request.TagGameEndRequest;
import kutaverse.game.websocket.taggame.dto.request.TagGameRequest;
import kutaverse.game.websocket.taggame.dto.request.TagGameResultStatus;
import kutaverse.game.websocket.taggame.dto.response.TagGameEndResponse;
import kutaverse.game.websocket.taggame.util.TagGameRoom;
import kutaverse.game.websocket.taggame.util.TagGameRoomManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class TagGameEndHandler implements CustomHandler {

    private ObjectMapper objectMapper = new ObjectMapper();
    private final TagGameUserRepository tagGameUserRepository;

    @Override
    public void handler(Object object, WebSocketSession session) {
        TagGameRequest tagGameRequest = (TagGameRequest) object;
        TagGameEndRequest tagGameEndRequest = (TagGameEndRequest) tagGameRequest.getRequest();
        TagGameRoom tagGameRoom = TagGameRoomManager.gameRooms.get(tagGameEndRequest.getRoomId());
        if (tagGameEndRequest.getTagGameResultStatus() == TagGameResultStatus.TAGGER_WIN) {
            taggerWin(tagGameRoom);
            TagGameRoomManager.deleteGameRoom(tagGameRoom.getRoomId());
            return;
        }
        playerWin(tagGameRoom);
        TagGameRoomManager.deleteGameRoom(tagGameRoom.getRoomId());
    }

    private void taggerWin(TagGameRoom tagGameRoom) {
        tagGameRoom.getPlayers().entrySet().forEach(player -> {
            tagGameUserRepository.get(player.getKey())
                    .doOnNext( tagGameUser -> {
                        if(tagGameUser.getRole() == Role.TAGGER){
                            sendLoseMessage(player.getValue());
                            return;
                        }
                        sendWinMessage(player.getValue());
                    })
                    .subscribe();
        });
    }

    private void playerWin(TagGameRoom tagGameRoom) {
        tagGameRoom.getPlayers().entrySet().forEach(player -> {
            tagGameUserRepository.get(player.getKey())
                    .doOnNext( tagGameUser -> {
                        if(tagGameUser.getRole() == Role.PLAYER){
                            sendLoseMessage(player.getValue());
                            return;
                        }
                        sendWinMessage(player.getValue());
                    }).subscribe();
        });
    }

    private void sendWinMessage(WebSocketSession webSocketSession) {
        String winMessage = "승리하였습니다.";
        TagGameEndResponse tagGameEndResponse = TagGameEndResponse.toDto(winMessage);
        try {
            String jsonMessage = objectMapper.writeValueAsString(tagGameEndResponse);
            WebSocketMessage webSocketMessage = webSocketSession.textMessage(jsonMessage);
            webSocketSession.send(Mono.just(webSocketMessage)).subscribe();
        } catch (JsonProcessingException e) {}
    }

    private void sendLoseMessage(WebSocketSession webSocketSession) {
        String winMessage = "패배하였습니다.";
        TagGameEndResponse tagGameEndResponse = TagGameEndResponse.toDto(winMessage);
        try {
            String jsonMessage = objectMapper.writeValueAsString(tagGameEndResponse);
            WebSocketMessage webSocketMessage = webSocketSession.textMessage(jsonMessage);
            webSocketSession.send(Mono.just(webSocketMessage)).subscribe();
        } catch (JsonProcessingException e) {}
    }
}
