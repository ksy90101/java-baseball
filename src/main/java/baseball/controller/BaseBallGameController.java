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

    public int gameStart(BaseBallNumberGenerator baseBallNumberGenerator, GameStartRequest gameStartRequest) {
        Computer computer = new Computer(baseBallNumberGenerator.generate());
        Game game = new Game(computer, gameStartRequest.limitPlayerTimes());

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

        return new CheckBallResponse(strikeCount,
                ballCount,
                playerRecord.isNotting(),
                playerRecord.isSuccess(),
                game.isFinished(),
                game.getWinner());
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
        int maxPlayerTimes = gameRepository.getMaxPlayerTimes();
        List<Integer> gameIdsOfMaxPlayerTimes = gameRepository.findAllByPlayerTimes(maxPlayerTimes)
                .stream()
                .map(Game::getId)
                .toList();
        int minPlayerTimes = gameRepository.getMinPlayerTimes();
        List<Integer> gameIdsOfMinPlayerTimes = gameRepository.findAllByPlayerTimes(minPlayerTimes)
                .stream()
                .map(Game::getId)
                .toList();

        double averagePlayerTimes = gameRepository.getAveragePlayerTimes();

        int maxCountLimitPlayerTimes = gameRepository.getMaxCountLimitPlayerTimes();
        List<Integer> gameIdsOfMaxCountLimitPlayerTimes = gameRepository.findIdsByLimitPlayerTimes(maxCountLimitPlayerTimes);

        int minCountLimitPlayerTimes = gameRepository.getMinCountLimitPlayerTimes();
        List<Integer> gameIdsOfMinCountLimitPlayerTimes = gameRepository.findIdsByLimitPlayerTimes(minCountLimitPlayerTimes);

        int maxLimitPlayerTimes = gameRepository.getMaxLimitPlayerTimes();
        List<Integer> gameIdsOfMaxLimitPlayerTimes = gameRepository.findIdsByLimitPlayerTimes(minCountLimitPlayerTimes);

        int minLimitPlayerTimes = gameRepository.getMinLimitPlayerTimes();
        List<Integer> gameIdsOfMinLimitPlayerTimes = gameRepository.findIdsByLimitPlayerTimes(minCountLimitPlayerTimes);

        int maxLimitPlayerTimesByWinnerComputer = gameRepository.getMaxLimitPlayerTimesByWinnerComputer();
        List<Integer> gameIdsOfMaxLimitPlayerTimesByWinnerComputer = gameRepository.findIdsByLimitPlayerTimes(minCountLimitPlayerTimes);

        int maxLimitPlayerTimesByWinnerPlayer = gameRepository.getMaxLimitPlayerTimesByWinnerPlayer();
        List<Integer> gameIdsOfMibLimitPlayerTimesByWinnerPlayer = gameRepository.findIdsByLimitPlayerTimes(minCountLimitPlayerTimes);

        double averageLimitPlayerTimes = gameRepository.getAverageLimitPlayerTimes();

        return new StatisticsResponse(
                maxPlayerTimes,
                gameIdsOfMaxPlayerTimes,
                minPlayerTimes,
                gameIdsOfMinPlayerTimes,
                averagePlayerTimes,
                maxCountLimitPlayerTimes,
                gameIdsOfMaxCountLimitPlayerTimes,
                minCountLimitPlayerTimes,
                gameIdsOfMinCountLimitPlayerTimes,
                maxLimitPlayerTimes,
                gameIdsOfMaxLimitPlayerTimes,
                minLimitPlayerTimes,
                gameIdsOfMinLimitPlayerTimes,
                maxLimitPlayerTimesByWinnerComputer,
                gameIdsOfMaxLimitPlayerTimesByWinnerComputer,
                maxLimitPlayerTimesByWinnerPlayer,
                gameIdsOfMibLimitPlayerTimesByWinnerPlayer,
                averageLimitPlayerTimes
        );
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
