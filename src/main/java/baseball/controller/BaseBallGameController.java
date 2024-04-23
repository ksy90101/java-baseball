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

    public BaseBallGameController(final GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public int gameStart(final BaseBallNumberGenerator baseBallNumberGenerator, final GameStartRequest gameStartRequest) {
        final Computer computer = new Computer(baseBallNumberGenerator.generate());
        final Game game = new Game(computer, gameStartRequest.limitPlayerTimes());

        return gameRepository.insert(game);
    }

    public CheckBallResponse checkBalls(final CheckBallsRequest checkBallsRequest) {
        final Game game = gameRepository.findById(checkBallsRequest.gameId())
                .orElseThrow(() -> new IllegalArgumentException("게임이 존재하지 않습니다."));

        final BaseBallNumbers playerNumbers = getPlayerNumbers(checkBallsRequest);
        final Computer computer = game.getComputer();
        final int strikeCount = computer.getStrikeCount(playerNumbers);
        final int ballCount = computer.getBallCount(playerNumbers, strikeCount);

        final PlayerRecord playerRecord = new PlayerRecord(
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

    public StatisticsResponse getStatistics() {
        final int maxPlayerTimes = gameRepository.getMaxPlayerTimes();
        final List<Integer> gameIdsOfMaxPlayerTimes = gameRepository.findAllByPlayerTimes(maxPlayerTimes)
                .stream()
                .map(Game::getId)
                .toList();
        final int minPlayerTimes = gameRepository.getMinPlayerTimes();
        final List<Integer> gameIdsOfMinPlayerTimes = gameRepository.findAllByPlayerTimes(minPlayerTimes)
                .stream()
                .map(Game::getId)
                .toList();

        final double averagePlayerTimes = gameRepository.getAveragePlayerTimes();

        final int maxCountLimitPlayerTimes = gameRepository.getMaxCountLimitPlayerTimes();
        final List<Integer> gameIdsOfMaxCountLimitPlayerTimes = gameRepository.findIdsByLimitPlayerTimes(maxCountLimitPlayerTimes, (game) -> true);

        final int minCountLimitPlayerTimes = gameRepository.getMinCountLimitPlayerTimes();
        final List<Integer> gameIdsOfMinCountLimitPlayerTimes = gameRepository.findIdsByLimitPlayerTimes(minCountLimitPlayerTimes, (game) -> true);

        final int maxLimitPlayerTimes = gameRepository.getMaxLimitPlayerTimes();
        final List<Integer> gameIdsOfMaxLimitPlayerTimes = gameRepository.findIdsByLimitPlayerTimes(minCountLimitPlayerTimes, (game) -> true);

        final int minLimitPlayerTimes = gameRepository.getMinLimitPlayerTimes();
        final List<Integer> gameIdsOfMinLimitPlayerTimes = gameRepository.findIdsByLimitPlayerTimes(minCountLimitPlayerTimes, (game) -> true);

        final int maxLimitPlayerTimesByWinnerComputer = gameRepository.getMaxLimitPlayerTimesByWinnerComputer();
        final List<Integer> gameIdsOfMaxLimitPlayerTimesByWinnerComputer = gameRepository.findIdsByLimitPlayerTimes(maxLimitPlayerTimesByWinnerComputer, Game::isComputerWin);

        final int maxLimitPlayerTimesByWinnerPlayer = gameRepository.getMaxLimitPlayerTimesByWinnerPlayer();
        final List<Integer> gameIdsOfMibLimitPlayerTimesByWinnerPlayer = gameRepository.findIdsByLimitPlayerTimes(minCountLimitPlayerTimes, Game::isPlayerWin);

        final double averageLimitPlayerTimes = gameRepository.getAverageLimitPlayerTimes();

        final int countByWinnerComputer = gameRepository.getCountByWinnerComputer();
        final int countByWinnerPlayer = gameRepository.getCountByWinnerPlayer();

        final int playerWinningPercentage = (int) ((double) countByWinnerPlayer / (countByWinnerComputer + countByWinnerPlayer) * 100);

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
                averageLimitPlayerTimes,
                countByWinnerComputer,
                countByWinnerPlayer,
                playerWinningPercentage
        );
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
        return new GameRecordResponse(playerRecordResponses);
    }
}
