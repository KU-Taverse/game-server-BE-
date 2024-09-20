package kutaverse.game.websocket.taggame.handler;

import kutaverse.game.websocket.taggame.dto.TagGameStatus;

import java.util.HashMap;
import java.util.Map;

public class CustomHandlerMapping {

    private static final Map<TagGameStatus, CustomHandler> map=new HashMap<>();


    static {
        map.put(TagGameStatus.TAG_GAME_WAITING_FOR_PLAYERS,new TagGameMatchingHandler());
        map.put(TagGameStatus.TAG_GAME_PLAYING_MOVING,new TagGameMovingHandler());
    }

    public static CustomHandler getHandler(TagGameStatus tagGameStatus){
        return map.get(tagGameStatus);
    }
}
