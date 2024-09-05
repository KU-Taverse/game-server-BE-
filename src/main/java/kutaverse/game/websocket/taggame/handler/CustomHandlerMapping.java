package kutaverse.game.websocket.taggame.handler;

import jakarta.annotation.PostConstruct;
import kutaverse.game.websocket.map.handler.WebSocketHandler;
import kutaverse.game.websocket.taggame.dto.TagGameStatus;

import java.util.HashMap;
import java.util.Map;

public class CustomHandlerMapping {

    private static final Map<TagGameStatus, CustomHandler> map=new HashMap<>();


    static {
        map.put(TagGameStatus.WAITING_FOR_PLAYERS,new TagGameMatchingHandler());
    }

    public static CustomHandler getHandler(TagGameStatus tagGameStatus){
        return map.get(tagGameStatus);
    }
}
