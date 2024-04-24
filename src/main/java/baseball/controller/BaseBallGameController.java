package baseball.controller;

import baseball.domain.*;
import baseball.dto.*;
import baseball.factory.BaseBallNumberFactory;
import baseball.generator.BaseBallNumberGenerator;
import baseball.repository.GameRepository;

import java.util.List;

public class BaseBallGameController {
    private final GameRepository gameRepository;

    public BaseBallGameController(final GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public int gameStart(final BaseBallNumberGenerator baseBallNumberGenerator) {
        final Computer computer = new Computer(baseBallNumberGenerator.generate());
        final Game game = new Game(computer);

        return gameRepository.insert(game);
    }

    public CheckBallResponse checkBalls(final CheckBallsRequest checkBallsRequest) {
        final Game game = gameRepository.findById(checkBallsRequest.gameId())
                .orElseThrow(() -> new IllegalArgumentException("게임이 존재하지 않습니다."));

        final BaseBallNumbers playerNumbers = getPlayerNumbers(checkBallsRequest);
        final Computer computer = game.getComputer();
        final Count strikeCount = computer.getStrikeCount(playerNumbers);
        final Count ballCount = computer.getBallCount(playerNumbers, strikeCount);

        final PlayerRecord playerRecord = new PlayerRecord(playerNumbers, strikeCount, ballCount);
        game.addPlayerRecord(playerRecord);

        return new CheckBallResponse(playerRecord.getStrikeCount(), playerRecord.getBallCount(), playerRecord.isNotting(), playerRecord.isSuccess());
    }

    private BaseBallNumbers getPlayerNumbers(final CheckBallsRequest checkBallsRequest) {
        return new BaseBallNumbers(
                checkBallsRequest.userNumbers()
                        .stream()
                        .map(BaseBallNumberFactory::valueOf)
                        .toList()
        );
    }

    public List<GameRecordsResponse> getGames() {
        final List<Game> games = gameRepository.findAll();
        return games.stream()
                .map(this::convertGameRecordsResponse)
                .toList();
    }

    public GameRecordResponse getGame(final int id) {
        final Game game = gameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게임 기록이 존재하지 않습니다."));

        return convertGameRecordResponse(game);
    }

    private GameRecordsResponse convertGameRecordsResponse(final Game game) {
        return new GameRecordsResponse(game.getId(),
                game.getStartAt(),
                game.getEndAt(),
                game.getPlayerTimes());
    }

    private GameRecordResponse convertGameRecordResponse(final Game game) {
        final List<PlayerRecordResponse> playerRecordResponses = game.getPlayerNumbers()
                .stream()
                .map(playerRecord -> new PlayerRecordResponse(playerRecord.getValueNumbers(),
                        playerRecord.getStrikeCount(),
                        playerRecord.getBallCount(),
                        playerRecord.isNotting(),
                        playerRecord.isSuccess()))
                .toList();
        return new GameRecordResponse(game.getId(), playerRecordResponses);
    }
}
