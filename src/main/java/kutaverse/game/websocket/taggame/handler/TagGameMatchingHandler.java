package kutaverse.game.websocket.taggame.handler;

import kutaverse.game.websocket.taggame.dto.TagGameMatchRequest;
import kutaverse.game.websocket.taggame.dto.TagGameRequest;
import kutaverse.game.websocket.taggame.util.TagGameMatchingQueue;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.socket.WebSocketSession;

@RequiredArgsConstructor
public class TagGameMatchingHandler implements CustomHandler{

    @Override
    public void handler(Object object, WebSocketSession webSocketSession) {
        TagGameRequest tagGameRequest =(TagGameRequest) object;
        TagGameMatchRequest tagGameMatchRequest = (TagGameMatchRequest) tagGameRequest.getRequest();
        TagGameMatchingQueue.addPlayer(tagGameMatchRequest.getUserId(),webSocketSession);
    }
}
