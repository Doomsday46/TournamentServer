package com.doomsday.tournamentserver.service.implement;

import com.doomsday.tournamentserver.db.Game;
import com.doomsday.tournamentserver.db.PrizePlace;
import com.doomsday.tournamentserver.db.Setting;
import com.doomsday.tournamentserver.db.Tournament;
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
import com.doomsday.tournamentserver.localization.TextProgram;
import com.doomsday.tournamentserver.mapper.*;
import com.doomsday.tournamentserver.service.TournamentService;
import com.doomsday.tournamentserver.service.model.information.SchemeInformation;
import com.doomsday.tournamentserver.service.model.information.TournamentInformation;
import com.doomsday.tournamentserver.service.model.information.TournamentSaveInformation;
import com.doomsday.tournamentserver.service.model.view.TournamentView;
import com.doomsday.tournamentserver.validator.TournamentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;

@Service
public class TournamentServiceImpl implements TournamentService {

    private PlayerRepository playerRepository;
    private LocationRepository locationRepository;
    private PrizePlaceRepository prizePlaceRepository;
    private TournamentRepository tournamentRepository;
    private SettingRepository settingRepository;
    private TournamentToTournamentInfoMapper tournamentToTournamentInfoMapper;
    private TournamentViewToTournamentMapper tournamentViewToTournamentMapper;
    private UserRepository userRepository;
    private TournamentValidator tournamentValidator;
    private PlayerDBToPlayerDomainMapper playerDBToPlayerDomainMapper;
    private LocationDBToLocationDomainMapper locationDBToLocationDomainMapper;
    private GameRepository gameRepository;


    private TextProgram textProgram;

    @Autowired
    public TournamentServiceImpl(PlayerRepository playerRepository, LocationRepository locationRepository, PrizePlaceRepository prizePlaceRepository, TournamentRepository tournamentRepository, SettingRepository settingRepository, TournamentToTournamentInfoMapper tournamentToTournamentInfoMapper, TournamentViewToTournamentMapper tournamentViewToTournamentMapper, UserRepository userRepository, TournamentValidator tournamentValidator, PlayerDBToPlayerDomainMapper playerDBToPlayerDomainMapper, LocationDBToLocationDomainMapper locationDBToLocationDomainMapper, GameRepository gameRepository) {
        this.playerRepository = playerRepository;
        this.locationRepository = locationRepository;
        this.prizePlaceRepository = prizePlaceRepository;
        this.tournamentRepository = tournamentRepository;
        this.settingRepository = settingRepository;
        this.tournamentToTournamentInfoMapper = tournamentToTournamentInfoMapper;
        this.tournamentViewToTournamentMapper = tournamentViewToTournamentMapper;
        this.userRepository = userRepository;
        this.tournamentValidator = tournamentValidator;
        this.playerDBToPlayerDomainMapper = playerDBToPlayerDomainMapper;
        this.locationDBToLocationDomainMapper = locationDBToLocationDomainMapper;
        this.gameRepository = gameRepository;

        this.textProgram = new TextProgram("textResponse", Locale.ENGLISH);
    }


    @Override
    public TournamentSaveInformation saveTournament(long idUser, String name) {
        if (idUser <= 0 || name.isEmpty()) throw new IllegalArgumentException("Incorrect parameters");

        try {
            var user = userRepository.getOne(idUser);

            if (tournamentRepository.existsByName(name))
                return new TournamentSaveInformation(-1, name, textProgram.getResourceBundle().getString("tournament.save.information.duplicate"));

            var tournament = new Tournament();

            tournament.setName(name);
            tournament.setFinished(false);
            tournament.setUser(user);

            var savedTournament = tournamentRepository.saveAndFlush(tournament);

            return new TournamentSaveInformation(savedTournament.getId(), name, textProgram.getResourceBundle().getString("tournament.save.information.succesfull"));
        } catch (Exception ex) {
            return new TournamentSaveInformation(-1, name, textProgram.getResourceBundle().getString("tournament.save.information.error"));
        }
    }

    @Override
    public long saveTournament(long idUser, TournamentView tournamentView) {
        if (idUser <= 0 || tournamentView == null) throw new IllegalArgumentException("Incorrect parameters");

        try {
            var user = userRepository.getOne(idUser);
            var tournament = tournamentViewToTournamentMapper.map(tournamentView);

            Setting setting;

            if (tournament.getSetting() == null) {
                tournamentRepository.saveAndFlush(tournament);
            } else {
                setting = tournament.getSetting();
                setting.setTournament(tournament);
                setting.setUser(user);
                tournament.setUser(user);
                var indexTournament = tournamentRepository.saveAndFlush(tournament);
                settingRepository.saveAndFlush(setting);
                return indexTournament.getId();
            }

        } catch (Exception ex) {
            return -1;
        }
        return -1;
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
            //game.setUser(tournament.getUser());
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

    private boolean isRightPlayer(Player player, com.doomsday.tournamentserver.db.Player playerDB){
        return player.getFirstName().equals(playerDB.getFirstName()) && player.getLastName().equals(playerDB.getSurname())
                && player.getAge() == Period.between(convertToLocalDateViaInstant(playerDB.getAge()), LocalDate.now()).getYears() && player.getNumber() == playerDB.getNumber();
    }

    @Override
    public boolean updateDataForTournament(long idUser, long idTournament, TournamentView tournamentView) {
        if (idUser <= 0 || idTournament <= 0 || tournamentView == null) throw new IllegalArgumentException("Incorrect parameters");


        try {
            var tournament = tournamentViewToTournamentMapper.map(tournamentView);
            var tournamentDB = tournamentRepository.findByIdAndUser_Id(idTournament, idUser);

            if (!tournamentDB.getFinished()) return false;

            tournamentDB.setName(tournament.getName());
            tournamentDB.setFinished(tournamentView.isFinished());

            if (tournament.getSetting() != null) {
                var settingView = tournamentView.getSettingView();

                tournamentDB.getSetting().setCountPlayers(settingView.getCountPlayers());
                tournamentDB.getSetting().setCountPrizePlace(settingView.getCountPrizePlace());
                tournamentDB.getSetting().setDurationMatch(LocalTime.of(settingView.getMathTimeSetting().getDurationMatchHour(),
                        settingView.getMathTimeSetting().getDurationMatchMinute(),
                        settingView.getMathTimeSetting().getDurationMatchSeconds()));
                tournamentDB.getSetting().setEndDate(settingView.getTournamentTimeSetting().getEndTournament());
                tournamentDB.getSetting().setEndGameDay(settingView.getTournamentTimeSetting().getEndGameDayTime());
                tournamentDB.getSetting().setTypeScheme(settingView.getTypeScheme());
                tournamentDB.getSetting().setStartGameDay(settingView.getTournamentTimeSetting().getBeginGameDayTime());
                tournamentDB.getSetting().setStartDate(settingView.getTournamentTimeSetting().getBeginTournament());
            }

            tournamentRepository.saveAndFlush(tournamentDB);

        } catch (Exception ex) {
            return false;
        }

        return true;
    }


    @Override
    public boolean deleteTournament(long idUser, long idTournament) {
        try {
            if (tournamentRepository.existsById(idTournament)) return false;

            tournamentRepository.deleteById(idTournament);

            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public Tournament getTournament(long idUser, long idTournament) {
        return tournamentRepository.findByIdAndUser_Id(idTournament, idUser);
    }

    @Override
    public TournamentInformation getTournamentInformation(long idUser, long idTournament) {
        return tournamentToTournamentInfoMapper.map(tournamentRepository.findByIdAndUser_Id(idTournament, idUser));
    }

    @Override
    public List<TournamentInformation> getTournamentInformation(long idUser) {

        if (idUser <= 0) throw new IllegalArgumentException("Incorrect parameters");

        var tournaments = tournamentRepository.findAllByUser_Id(idUser);

        var tournamentInformations = new ArrayList<TournamentInformation>();

        tournaments.forEach(a -> tournamentInformations.add(tournamentToTournamentInfoMapper.map(a)));

        return tournamentInformations;
    }

    @Override
    public List<TournamentInformation> getTournamentInformation(long idUser, String nameTournament) {
        return null;
    }

    @Override
    public List<TournamentInformation> getTournamentInformation(long idUser, String nameTournament, Date beginInterval, Date endInterval) {
        return null;
    }

    @Override
    public List<TournamentInformation> getTournamentInformation(long idUser, String nameTournament, Date beginInterval) {
        return null;
    }

    @Override
    public SchemeInformation getTournamentSchemeDetails(long idUser, long idTournament) {
        return null;
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
