package kutaverse.game.websocket.taggame.dto;

public enum TagGameStatus {

    //매칭 중
    TAG_GAME_WAITING_FOR_PLAYERS,
    //게임 이동
    TAG_GAME_PLAYING_MOVING,
    //플레이어 잡기
    TAG_GAME_TAGGING,
    //게임 종료
    TAG_GAME_END
}
