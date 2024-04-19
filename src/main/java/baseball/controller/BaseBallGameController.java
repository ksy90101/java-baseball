package baseball.controller;

import baseball.domain.BaseBallNumbers;
import baseball.domain.Computer;
import baseball.domain.Game;
import baseball.domain.PlayerRecord;
import baseball.dto.*;
import baseball.factory.BaseBallNumberFactory;
import baseball.generator.BaseBallNumberGenerator;
import baseball.repository.GameRepository;

import java.util.List;

public class BaseBallGameController {
    private final GameRepository gameRepository;

    public BaseBallGameController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public int gameStart(BaseBallNumberGenerator baseBallNumberGenerator) {
        Computer computer = new Computer(baseBallNumberGenerator.generate());
        Game game = new Game(computer);

        return gameRepository.insert(game);
    }

    public CheckBallResponse checkBalls(CheckBallsRequest checkBallsRequest) {
        Game game = gameRepository.findById(checkBallsRequest.gameId())
                .orElseThrow(() -> new IllegalArgumentException("게임이 존재하지 않습니다."));

        BaseBallNumbers playerNumbers = getPlayerNumbers(checkBallsRequest);
        Computer computer = game.getComputer();
        int strikeCount = computer.getStrikeCount(playerNumbers);
        int ballCount = computer.getBallCount(playerNumbers, strikeCount);

        PlayerRecord playerRecord = new PlayerRecord(
                playerNumbers,
                strikeCount,
                ballCount
        );
        game.addPlayerRecord(playerRecord);

        if (playerRecord.isSuccess()) {
            game.end();
        }

        return new CheckBallResponse(strikeCount, ballCount, playerRecord.isNotting(), playerRecord.isSuccess());
    }

    private BaseBallNumbers getPlayerNumbers(CheckBallsRequest checkBallsRequest) {
        return new BaseBallNumbers(
                checkBallsRequest.userNumbers()
                        .stream()
                        .map(BaseBallNumberFactory::valueOf)
                        .toList()
        );
    }

    public List<GameRecordsResponse> getGames() {
        List<Game> games = gameRepository.findAll();
        return games.stream()
                .map(this::convertGameRecordsResponse)
                .toList();
    }

    public GameRecordResponse getGame(int id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게임 기록이 존재하지 않습니다."));

        return convertGameRecordResponse(game);
    }

    public StatisticsResponse getStatistics() {
        int minPlayerTimes = gameRepository.getMinPlayerTimes();
        int maxPlayerTimes = gameRepository.getMaxPlayerTimes();
        List<Integer> gameIdsOfMinPlayerTimes = gameRepository.findAllByPlayerTimes(minPlayerTimes)
                .stream()
                .map(Game::getId)
                .toList();
        List<Integer> gameIdsOfMaxPlayerTimes = gameRepository.findAllByPlayerTimes(maxPlayerTimes)
                .stream()
                .map(Game::getId)
                .toList();
        double averagePlayerTimes = gameRepository.getAveragePlayerTimes();
        
        return new StatisticsResponse(
                minPlayerTimes,
                gameIdsOfMinPlayerTimes,
                maxPlayerTimes,
                gameIdsOfMaxPlayerTimes,
                averagePlayerTimes);
    }

    private GameRecordsResponse convertGameRecordsResponse(Game game) {
        return new GameRecordsResponse(game.getId(),
                game.getStartAt(),
                game.getEndAt(),
                game.getPlayerTimes());
    }

    private GameRecordResponse convertGameRecordResponse(Game game) {
        List<PlayerRecordResponse> playerRecordResponses = game.getPlayerNumbers()
                .stream()
                .map(playerRecord -> new PlayerRecordResponse(playerRecord.getValueNumbers(),
                        playerRecord.getStrikeCount(),
                        playerRecord.getBallCount(),
                        playerRecord.isNotting(),
                        playerRecord.isSuccess()))
                .toList();
        return new GameRecordResponse(playerRecordResponses);
    }
}
