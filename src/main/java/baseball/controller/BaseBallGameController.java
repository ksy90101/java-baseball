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
        // 플레이어가 가장 많이 시도한 횟수
        int maxPlayerTimes = gameRepository.getMaxPlayerTimes();
        List<Integer> gameIdsOfMaxPlayerTimes = gameRepository.findAllByPlayerTimes(maxPlayerTimes)
                .stream()
                .map(Game::getId)
                .toList();
        // 플레이어가 가장 적게 시도한 횟수
        int minPlayerTimes = gameRepository.getMinPlayerTimes();
        List<Integer> gameIdsOfMinPlayerTimes = gameRepository.findAllByPlayerTimes(minPlayerTimes)
                .stream()
                .map(Game::getId)
                .toList();

        // 사용자가 평균적으로 시도한 횟수
        double averagePlayerTimes = gameRepository.getAveragePlayerTimes();

        // 가장 많이 적용된 승리/패패 횟수
        int maxCountLimitPlayerTimes = gameRepository.getMaxCountLimitPlayerTimes();
        List<Integer> gameIdsOfMaxCountLimitPlayerTimes = gameRepository.findIdsByLimitPlayerTimes(maxCountLimitPlayerTimes);

        // 가장 적게 적용된 승리/패패 횟수
        int minCountLimitPlayerTimes = gameRepository.getMinCountLimitPlayerTimes();
        List<Integer> gameIdsOfMinCountLimitPlayerTimes = gameRepository.findIdsByLimitPlayerTimes(minCountLimitPlayerTimes);

        // 가장 큰 값으로 적용된 승리/패패 횟수
        int maxLimitPlayerTimes = gameRepository.getMaxLimitPlayerTimes();
        List<Integer> gameIdsOfMaxLimitPlayerTimes = gameRepository.findIdsByLimitPlayerTimes(minCountLimitPlayerTimes);

        // 가장 적은 값으로 적용된 승리/패패 횟수
        int minLimitPlayerTimes = gameRepository.getMinLimitPlayerTimes();
        List<Integer> gameIdsOfMinLimitPlayerTimes = gameRepository.findIdsByLimitPlayerTimes(minCountLimitPlayerTimes);

        //  - 컴퓨터가 가장 많이 승리한 승리/패패 횟수
        int maxLimitPlayerTimesByWinnerComputer = gameRepository.getMaxLimitPlayerTimesByWinnerComputer();
        List<Integer> gameIdsOfMaxLimitPlayerTimesByWinnerComputer = gameRepository.findIdsByLimitPlayerTimes(minCountLimitPlayerTimes);

        //  - 사용자가 가장 많이 승리한 승리/패패 횟수
        int maxLimitPlayerTimesByWinnerPlayer = gameRepository.getMaxLimitPlayerTimesByWinnerPlayer();
        List<Integer> gameIdsOfMibLimitPlayerTimesByWinnerComputer = gameRepository.findIdsByLimitPlayerTimes(minCountLimitPlayerTimes);

        // - 평균 승리/패패 횟수
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
                gameIdsOfMibLimitPlayerTimesByWinnerComputer,
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
