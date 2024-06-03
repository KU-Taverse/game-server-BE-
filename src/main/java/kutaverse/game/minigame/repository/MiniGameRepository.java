package kutaverse.game.minigame.repository;

import kutaverse.game.minigame.domain.GameResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MiniGameRepository {
    // 해당 방 게임 결과의 데이터 가져오기
    Mono<GameResult> findByRoomId(Long id);

    // 방 생성 시 초기화
    Mono<GameResult> save(GameResult gameResult);

    // 게임 종료 시 방 데이터 변경
    Mono<GameResult> update(GameResult gameResult);

    // 해당 유저 게임 정보 가져오기
    Flux<GameResult> findByUserId(String userId);

    // 해당 데이터 삭제
    Mono<Void> delete(Long id);

}
