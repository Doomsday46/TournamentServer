package com.doomsday.tournamentserver.service.implement;

import com.doomsday.tournamentserver.db.Entity.Game;
import com.doomsday.tournamentserver.db.Entity.PrizePlace;
import com.doomsday.tournamentserver.db.Entity.Tournament;
import com.doomsday.tournamentserver.db.repository.*;
import com.doomsday.tournamentserver.domain.builder.TournamentBuilder;
import com.doomsday.tournamentserver.domain.builder.UniversalTournamentBuilder;
import com.doomsday.tournamentserver.domain.model.Location;
import com.doomsday.tournamentserver.domain.model.Match;
import com.doomsday.tournamentserver.domain.model.Player;
import com.doomsday.tournamentserver.domain.schedule.ScheduleGeneratorImpl;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;

public class TournamentManagerImplService implements TournamentManagerService {

    private TournamentRepository tournamentRepository;
    private TournamentValidator tournamentValidator;
    private GameRepository gameRepository;
    private PrizePlaceRepository prizePlaceRepository;
    private PlayerRepository playerRepository;
    private LocationRepository locationRepository;
    private PlayerDBToPlayerDomainMapper playerDBToPlayerDomainMapper;
    private LocationDBToLocationDomainMapper locationDBToLocationDomainMapper;

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
    public void finishMatch(MatchView matchView) {

    }

    @Override
    public void finishMatches(List<MatchView> matches) {

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

        var dateMatch = new Date(match.getDate().getYear(),
                match.getDate().getMonthValue(),
                match.getDate().getDayOfYear(),
                match.getDate().getHour(),
                match.getDate().getMinute(),
                match.getDate().getSecond());

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


        game.setPlayers(players);
        game.setState(false);

        return  game;
    }

    private boolean isRightPlayer(Player player, com.doomsday.tournamentserver.db.Entity.Player playerDB){
        return player.getFirstName().equals(playerDB.getFirstName()) && player.getLastName().equals(playerDB.getSurname())
                && player.getAge() == Period.between(convertToLocalDateViaInstant(playerDB.getAge()), LocalDate.now()).getYears() && player.getNumber() == playerDB.getNumber();
    }

    private com.doomsday.tournamentserver.domain.tournament.Tournament createTournament(Tournament tournament){
        TournamentBuilder tournamentBuilder = new UniversalTournamentBuilder(tournament);

        var playerDomainService = new DomainPlayerService();
        var locationDomainService = new DomainLocationService();
        var dateDomainService = new DomainDateService();

        var players = tournament.getPlayers();
        var playersDomain = new ArrayList<Player>();
        players.forEach(a -> playersDomain.add(playerDBToPlayerDomainMapper.map(a)));

        int number = 1;
        for (var playerDomain:playersDomain) {
            playerDomain.setNumber(number);
            number++;
        }

        playerDomainService.addNewPlayers(playersDomain);

        var locations = tournament.getLocations();
        var locationsDomain = new ArrayList<Location>();
        locations.forEach(a -> locationsDomain.add(locationDBToLocationDomainMapper.map(a)));
        locationDomainService.addAllLocation(locationsDomain);

        var durationMatch = tournament.getSetting().getDurationMatch().getHour() * 60 +
                tournament.getSetting().getDurationMatch().getMinute() +
                tournament.getSetting().getDurationMatch().getSecond() / 60.0;

        var timeSetting  = new TimeSetting(tournament.getSetting().getStartGameDay().getHour(),
                tournament.getSetting().getEndGameDay().getHour(), durationMatch);

        var endDate = map(tournament.getSetting().getEndDate());
        var startDate = map(tournament.getSetting().getStartDate());

        dateDomainService.setTimeSetting(startDate, timeSetting);
        dateDomainService.setEndDate(endDate);


        var schemeType = SchemeType.valueOf(tournament.getSetting().getTypeScheme());

        var scheme = new SchemeStrategy().getScheme(schemeType, tournament.getSetting().getCountPlayers());

        var generateSchedule = new ScheduleGeneratorImpl(playerDomainService, locationDomainService, dateDomainService, scheme);

        var schedule = generateSchedule.generateSchedule();

        return tournamentBuilder.setPlayerService(playerDomainService).setLocationService(locationDomainService).setDateService(dateDomainService).
                setGenerateSchedule(generateSchedule).setSchedule(schedule).initSetting().build();
    }

    private LocalDateTime map(Date date){
        return LocalDateTime.of(date.getYear(),
                date.getMonth(),
                date.getDay(),
                date.getHours(),
                date.getMinutes(),
                date.getSeconds());
    }

    private LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
