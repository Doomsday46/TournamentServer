package com.doomsday.tournamentserver.service.implement;

import com.doomsday.tournamentserver.database.Entity.Game;
import com.doomsday.tournamentserver.database.Entity.PrizePlace;
import com.doomsday.tournamentserver.database.Entity.Tournament;
import com.doomsday.tournamentserver.database.repository.*;
import com.doomsday.tournamentserver.domain.builder.TournamentBuilder;
import com.doomsday.tournamentserver.domain.builder.UniversalTournamentBuilder;
import com.doomsday.tournamentserver.domain.model.*;
import com.doomsday.tournamentserver.domain.schedule.ScheduleGeneratorImpl;
import com.doomsday.tournamentserver.domain.scheme.Scheme;
import com.doomsday.tournamentserver.domain.scheme.SchemeStrategy;
import com.doomsday.tournamentserver.domain.scheme.SchemeType;
import com.doomsday.tournamentserver.domain.service.DomainDateService;
import com.doomsday.tournamentserver.domain.service.DomainLocationService;
import com.doomsday.tournamentserver.domain.service.DomainPlayerService;
import com.doomsday.tournamentserver.domain.setting.TimeSetting;
import com.doomsday.tournamentserver.mapper.LocationDBToLocationDomainMapper;
import com.doomsday.tournamentserver.mapper.PlayerDBToPlayerDomainMapper;
import com.doomsday.tournamentserver.service.TournamentManagerService;
import com.doomsday.tournamentserver.service.model.view.MatchView;
import com.doomsday.tournamentserver.validator.TournamentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
@Service
public class TournamentManagerImplService implements TournamentManagerService {

    private TournamentRepository tournamentRepository;
    private TournamentValidator tournamentValidator;
    private GameRepository gameRepository;
    private PrizePlaceRepository prizePlaceRepository;
    private PlayerRepository playerRepository;
    private LocationRepository locationRepository;
    private PlayerDBToPlayerDomainMapper playerDBToPlayerDomainMapper;
    private LocationDBToLocationDomainMapper locationDBToLocationDomainMapper;
    private DomainPlayerService playerDomainService;
    private DomainLocationService locationDomainService;
    private DomainDateService dateDomainService;
    private Scheme scheme;

    @Autowired
    public TournamentManagerImplService(TournamentRepository tournamentRepository, TournamentValidator tournamentValidator, GameRepository gameRepository, PrizePlaceRepository prizePlaceRepository, PlayerRepository playerRepository, LocationRepository locationRepository, PlayerDBToPlayerDomainMapper playerDBToPlayerDomainMapper, LocationDBToLocationDomainMapper locationDBToLocationDomainMapper) {
        this.tournamentRepository = tournamentRepository;
        this.tournamentValidator = tournamentValidator;
        this.gameRepository = gameRepository;
        this.prizePlaceRepository = prizePlaceRepository;
        this.playerRepository = playerRepository;
        this.locationRepository = locationRepository;
        this.playerDBToPlayerDomainMapper = playerDBToPlayerDomainMapper;
        this.locationDBToLocationDomainMapper = locationDBToLocationDomainMapper;
    }

    @Override
    public boolean finishMatch(long idUser, MatchView matchView) {
       var tournamentDB = tournamentRepository.findByIdAndUser_Id(matchView.getIdTournament(), idUser);
       if (tournamentDB.isFinished() || !tournamentDB.isStarted()) return false;
       var tournamentDomain = recoveryTournament(tournamentDB);
       tournamentDomain.start();

       var game = tournamentDB.getGames().stream().filter(a -> a.getId() == matchView.getIdMatch()).findFirst().get();
       game.setState(true);
       game.setScoreFirstSide(matchView.getScoreFirstPlayer());
       game.setScoreSecondSide(matchView.getScoreSecondPlayer());
       var match = createMatch(game);

       tournamentDomain.finishMatch(match, new Score(game.getScoreFirstSide(), game.getScoreSecondSide()));

        var isFinished = tournamentDomain.getSchedule().getMatchesByState(MatchState.NOTPLAYED).size() == 0;

        if (isFinished) {
            finishTournament(tournamentDB, tournamentDomain);
            updateLocations(tournamentDB, tournamentDomain);
            updateGames(tournamentDB, tournamentDomain);
            return true;
        }

        updateLocations(tournamentDB, tournamentDomain);
        updateGames(tournamentDB, tournamentDomain);

       return true;
    }

    private void finishTournament(Tournament tournament, com.doomsday.tournamentserver.domain.tournament.Tournament tournamentDomain){
        tournamentDomain.finish();
        var winnerList = tournamentDomain.getPrizePlace();

        var prizePlaces = tournament.getPrizePlace();

        for (var prizePlace: prizePlaces) {
            var winnerPrizePlace = winnerList.stream().filter(a -> a.getPrizePlace() == prizePlace.getNumber()).findFirst().get();
            var player = tournament.getPlayers().stream().filter(a -> a.getFirstName().equals(winnerPrizePlace.getPlayer().getFirstName())
            && a.getSurname().equals(winnerPrizePlace.getPlayer().getLastName()) && a.getNumber() == winnerPrizePlace.getPlayer().getNumber()).findFirst().get();

            prizePlace.setPlayer(player);
        }

        tournament.setFinished(true);
        tournament.setPrizePlace(prizePlaces);
        tournamentRepository.saveAndFlush(tournament);
    }

    private List<Match> updateMatches(List<Game> games, List<Match> matches){
        /*
        for (var match: matches) {
            boolean exist = games.stream().anyMatch(a -> a.getNumberFirstSide() == match.getFirstSide().getNumber()
                    && a.getNumberSecondSide() == match.getSecondSide().getNumber() && a.getDate().equals(convertToDateViaSqlTimestamp(match.getDate())));
            if (exist) {
                var game = games.stream().filter(a -> a.getNumberFirstSide() == match.getFirstSide().getNumber()
                        && a.getNumberSecondSide() == match.getSecondSide().getNumber() && a.getDate().equals(convertToDateViaSqlTimestamp(match.getDate()))).findFirst().get();
                var matchState = game.isState() ? MatchState.PLAYED : MatchState.NOTPLAYED;
                match.setMatchState(matchState);
                match.
            }
        }
        */
        return null;
    }

    private void updateGames(Tournament tournament, com.doomsday.tournamentserver.domain.tournament.Tournament tournamentDomain){
        var games = tournament.getGames();
        var matches = tournamentDomain.getSchedule().getAllMatches();

        var updateGames = matches.stream().map(a -> createGame(a, tournament)).collect(Collectors.toList());

        for (var game: updateGames) {
            if (games.stream().anyMatch(a -> a.getDate().equals(game.getDate()) && a.getNumberSecondSide() == game.getNumberSecondSide()
            && a.getNumberFirstSide() == game.getNumberFirstSide() )) {
                var _game = games.stream().filter(a -> a.getDate().equals(game.getDate()) && a.getNumberSecondSide() == game.getNumberSecondSide()
                        && a.getNumberFirstSide() == game.getNumberFirstSide()).findFirst().get();

                _game.setScoreSecondSide(game.getScoreSecondSide());
                _game.setScoreFirstSide(game.getScoreFirstSide());
                _game.setState(game.isState());
                _game.setWinnerId(game.getWinnerId());

            } else {
                game.setTournament(tournament);
                games.add(game);
            }
        }

        tournament.setGames(games);

        gameRepository.saveAll(games);
        gameRepository.flush();
        tournamentRepository.saveAndFlush(tournament);

    }


    @Override
    public boolean finishMatches(long idUser, List<MatchView> matches) {
        for (var match: matches) {
            finishMatch(idUser, match);
        }
        return true;
    }


    private com.doomsday.tournamentserver.domain.tournament.Tournament recoveryTournament(Tournament tournament){
        TournamentBuilder tournamentBuilder = new UniversalTournamentBuilder(tournament);

        var matches = new ArrayList<Match>();

        tournament.getGames().forEach(a -> {
               matches.add(createMatch(a));
        });

        initParametersForTournament(tournament);

        var players = tournament.getPlayers();
        var playersDomain = new ArrayList<Player>();
        players.forEach(a -> playersDomain.add(playerDBToPlayerDomainMapper.map(a)));

        playerDomainService.addNewPlayers(playersDomain);



        var generateSchedule = new ScheduleGeneratorImpl(playerDomainService, locationDomainService, dateDomainService, scheme);

        var schedule = generateSchedule.recoverySchedule(matches);

        return tournamentBuilder.setPlayerService(playerDomainService).setLocationService(locationDomainService).setDateService(dateDomainService).
                setGenerateSchedule(generateSchedule).setSchedule(schedule).initSetting().build();
    }


    private void initParametersForTournament(Tournament tournament){
        playerDomainService = new DomainPlayerService();
        locationDomainService = new DomainLocationService();
        dateDomainService = new DomainDateService();

        var locations = tournament.getLocations();
        var locationsDomain = new ArrayList<Location>();
        locations.forEach(a -> locationsDomain.add(locationDBToLocationDomainMapper.map(a)));
        locationDomainService.addAllLocation(locationsDomain);

        var durationMatch = tournament.getSetting().getDurationMatch().getHour() * 60 +
                tournament.getSetting().getDurationMatch().getMinute() +
                tournament.getSetting().getDurationMatch().getSecond() / 60.0;

        double offsetMinutes = 30.0;
        var timeSetting  = new TimeSetting(tournament.getSetting().getStartGameDay().getHour(),
                tournament.getSetting().getEndGameDay().getHour(), durationMatch + offsetMinutes);

        var endDate = convertToLocalDateTimeViaInstant(tournament.getSetting().getEndDate());
        var startDate = convertToLocalDateTimeViaInstant(tournament.getSetting().getStartDate());

        dateDomainService.setTimeSetting(startDate, timeSetting);
        dateDomainService.setEndDate(endDate);


        var schemeType = SchemeType.valueOf(tournament.getSetting().getTypeScheme());

        scheme = new SchemeStrategy().getScheme(schemeType, tournament.getSetting().getCountPlayers());
    }

    @Override
    public boolean createTournament(long idUser, long idTournament) {

        Tournament tournament;
        try{
            tournament = tournamentRepository.findByIdAndUser_Id(idTournament, idUser);
        } catch (Exception ex) {
            return false;
        }

        if (!tournamentValidator.setModel(tournament).isValid()) return false;

        var tournamentDomain = createTournament(tournament);

        updatePlayers(tournament, tournamentDomain);
        updateLocations(tournament, tournamentDomain);


        var matchList = tournamentDomain.getSchedule().getAllMatches();


        var games = tournament.getGames();

        for (var match: matchList) {
            var game = createGame(match, tournament);
            game.setTournament(tournament);
            game.getPlayers().forEach(a -> a.addGames(game));
            game.getLocation().setGame(game);
            games.add(game);

        }

        gameRepository.saveAll(games);

        createPrizePlace(tournament, tournamentDomain);

        tournament.setStarted(true);
        tournamentRepository.saveAndFlush(tournament);

        return true;
    }

    private void createPrizePlace(Tournament tournament, com.doomsday.tournamentserver.domain.tournament.Tournament tournamentDomain){
        var countPrizePlace = tournamentDomain.getTournamentSetting().getPrizePlacesCount();

        Set<PrizePlace> prizePlaceSet = new HashSet();

        for (var i = 1; i <= countPrizePlace; i++){
            var  prizePlace = new PrizePlace();
            prizePlace.setNumber(i);
            prizePlace.setTournament(tournament);
            prizePlace.setUser(tournament.getUser());
            prizePlaceSet.add(prizePlace);
        }

        tournament.getPrizePlace().clear();
        tournament.setPrizePlace(prizePlaceSet);

        prizePlaceRepository.saveAll(prizePlaceSet);
        prizePlaceRepository.flush();
    }

    private void updatePlayers(Tournament tournament, com.doomsday.tournamentserver.domain.tournament.Tournament tournamentDomain){
        var playersDB = tournament.getPlayers();
        var players = tournamentDomain.getPlayerService().getAllPlayers();

        for (var playerDB : playersDB) {
            var player = players.stream().filter(a -> a.getFirstName().equals(playerDB.getFirstName()) && a.getLastName().equals(playerDB.getSurname())
                    && a.getAge() == Period.between(convertToLocalDateViaInstant(playerDB.getAge()), LocalDate.now()).getYears()).findAny().orElse(null);

            if (player != null) {
                playerDB.setNumber(player.getNumber());
            }
        }

        playerRepository.saveAll(playersDB);
        playerRepository.flush();
    }

    private void updateLocations(Tournament tournament, com.doomsday.tournamentserver.domain.tournament.Tournament tournamentDomain){
        var locationList = tournamentDomain.getLocationService().getAllLocations();
        var locationsDB = tournament.getLocations();

        for (var locationDB : locationsDB) {
            var location = locationList.stream().filter(a -> a.getPlace().equals(locationDB.getName())
                    && a.getDescription().equals(locationDB.getDescription())).findAny().orElse(null);

            if (location != null) {
                locationDB.setState(location.isBusy());
            }
        }

        locationRepository.saveAll(locationsDB);
        locationRepository.flush();
    }

    private Game createGame(Match match, Tournament tournament){
        var game = new Game();

        var dateMatch = convertToDateViaSqlTimestamp(match.getDate());
        game.setDate(dateMatch);

        for (var location: tournament.getLocations()) {
            if ( location.getName().equals(match.getLocation().getPlace()) &&  location.getDescription().equals(match.getLocation().getDescription()))
                location.setState(match.getLocation().isBusy());
            game.setLocation(location);
        }


        var players = new HashSet();

        for (var player: tournament.getPlayers()) {
            if (isRightPlayer(match.getFirstSide(), player) || isRightPlayer(match.getSecondSide(), player)) {
                players.add(player);
            }
        }

        game.setNumberFirstSide(match.getFirstSide().getNumber());
        game.setNumberSecondSide(match.getSecondSide().getNumber());
        game.setPlayers(players);
        game.setState(match.isPlayed());
        game.setScoreFirstSide(match.getPointsFirstSide());
        game.setScoreSecondSide(match.getPointsSecondSide());
        try {
            game.setWinnerId(game.getPlayers().stream().filter(a -> a.getNumber() == match.getWinner().getNumber()).findFirst().get().getId());
        } catch (Exception ex) {
            game.setWinnerId(-1);
        }

        return  game;
    }

    private boolean isRightPlayer(Player player, com.doomsday.tournamentserver.database.Entity.Player playerDB){
        return player.getFirstName().equals(playerDB.getFirstName()) && player.getLastName().equals(playerDB.getSurname())
                && player.getAge() == Period.between(convertToLocalDateViaInstant(playerDB.getAge()), LocalDate.now()).getYears() && player.getNumber() == playerDB.getNumber();
    }

    private com.doomsday.tournamentserver.domain.tournament.Tournament createTournament(Tournament tournament){
        TournamentBuilder tournamentBuilder = new UniversalTournamentBuilder(tournament);

        var players = tournament.getPlayers();
        var playersDomain = new ArrayList<Player>();
        players.forEach(a -> playersDomain.add(playerDBToPlayerDomainMapper.map(a)));

        initParametersForTournament(tournament);

        int number = 1;
        for (var playerDomain:playersDomain) {
            playerDomain.setNumber(number);
            number++;
        }

        playerDomainService.addNewPlayers(playersDomain);



        var generateSchedule = new ScheduleGeneratorImpl(playerDomainService, locationDomainService, dateDomainService, scheme);

        var schedule = generateSchedule.generateSchedule();

        return tournamentBuilder.setPlayerService(playerDomainService).setLocationService(locationDomainService).setDateService(dateDomainService).
                setGenerateSchedule(generateSchedule).setSchedule(schedule).initSetting().build();
    }

    private LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    private Match createMatch(Game game) {
        var firstSide = playerDBToPlayerDomainMapper.map(game.getPlayers().stream().filter(a -> a.getNumber() == game.getNumberFirstSide()).findFirst().get());
        var secondSide = playerDBToPlayerDomainMapper.map(game.getPlayers().stream().filter(a -> a.getNumber() == game.getNumberSecondSide()).findFirst().get());

        var location = locationDBToLocationDomainMapper.map(game.getLocation());
        var date = game.getDate();

        var match = new OneOnOneMatch(firstSide, secondSide, location, convertToLocalDateTimeViaInstant(date));

        var matchState = game.isState() ? MatchState.PLAYED : MatchState.NOTPLAYED;

        match.setPoints(game.getScoreFirstSide(), game.getScoreSecondSide());
        match.setMatchState(matchState);

        return match;
    }

    private LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    private Date convertToDateViaSqlTimestamp(LocalDateTime dateToConvert) {
        return java.sql.Timestamp.valueOf(dateToConvert);
    }
}
