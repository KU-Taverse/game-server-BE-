package kutaverse.game.minigame.service;

import kutaverse.game.minigame.domain.GameResult;
import kutaverse.game.minigame.repository.MiniGameReactiveRepository;
import kutaverse.game.minigame.repository.MiniGameRepository;
import kutaverse.game.websocket.minigame.dto.GameResultDTO;
import kutaverse.game.websocket.minigame.dto.GameUpdateDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@Slf4j
@RequiredArgsConstructor
public class MiniGameServiceImpl implements MiniGameService{
    private final MiniGameRepository mgrRepository  ;

    @Override
    public Mono<GameResult> createGameResult(GameResultDTO gameResultDTO) {
        GameResult gameResult = gameResultDTO.toEntity();
        return mgrRepository.save(gameResult);
    }

    @Transactional
    public Mono<GameResult> updateGameResult(GameUpdateDTO gameUpdateDTO){
        return mgrRepository.findByRoomId(gameUpdateDTO.getRoomId())
                .flatMap(gameResult -> {
                    gameResult.update(gameUpdateDTO);
                    log.info(String.valueOf(gameUpdateDTO.getPlayer1Score()));
                    return mgrRepository.save(gameResult);
                });
    }


    @Override
    public Mono<Void> deleteGameResult(Long id) {
        return mgrRepository.delete(id);
    }

    @Override
    public Flux<GameResult> findGameResultsByPlayerId(String playerId) {
        return mgrRepository.findByPlayerId(playerId);
    }

    @Override
    public Mono<GameResult> findGameResultById(String id) {
        return mgrRepository.findByRoomId(id);
    }
}
