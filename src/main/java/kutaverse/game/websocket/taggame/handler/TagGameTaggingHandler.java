package kutaverse.game.websocket.taggame.handler;

import kutaverse.game.taggame.service.TagGameUserService;
import kutaverse.game.websocket.taggame.dto.request.TagGameRequest;
import kutaverse.game.websocket.taggame.dto.request.TagGameTaggingRequest;
import kutaverse.game.websocket.taggame.dto.request.TagGameUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketSession;

@RequiredArgsConstructor
@Component
public class TagGameTaggingHandler implements CustomHandler{

    private final TagGameUserService tagGameUserService;

    @Override
    public void handler(Object object, WebSocketSession session) {
        TagGameRequest tagGameRequest =(TagGameRequest) object;
        TagGameTaggingRequest tagGameTaggingRequest = (TagGameTaggingRequest) tagGameRequest.getRequest();
        tagGameUserService.taggingUser(tagGameTaggingRequest.getRoomId()
                ,tagGameTaggingRequest.getTaggerId()
                ,tagGameTaggingRequest.getTaggedPlayerId())
                .subscribe();
    }
}
