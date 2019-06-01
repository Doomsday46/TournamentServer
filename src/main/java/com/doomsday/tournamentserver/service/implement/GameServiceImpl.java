package com.doomsday.tournamentserver.service.implement;

import com.doomsday.tournamentserver.db.Entity.Game;
import com.doomsday.tournamentserver.db.repository.GameRepository;
import com.doomsday.tournamentserver.db.repository.TournamentRepository;
import com.doomsday.tournamentserver.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    private GameRepository gameRepository;
    private TournamentRepository tournamentRepository;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, TournamentRepository tournamentRepository) {
        this.gameRepository = gameRepository;
        this.tournamentRepository = tournamentRepository;
    }

    @Override
    public boolean save(long idUser, long idTournament, Game game) {
        if (idUser <= 0 && idTournament <= 0 && game == null) return  false;

        var tournament = tournamentRepository.findByIdAndUser_Id(idTournament, idUser);

        game.setTournament(tournament);

        return true;
    }

    @Override
    public boolean update(Game game) {
        if (game == null && game.getId() <= 0) return  false;

        var _game = gameRepository.findById(game.getId()).get();

        _game.setState(game.isState());
        _game.setPlayers(game.getPlayers());
        _game.setLocation(game.getLocation());
        _game.setScoreFirstSide(game.getScoreFirstSide());
        _game.setScoreSecondSide(game.getScoreSecondSide());
        _game.setDate(game.getDate());

        _game.setTournament(game.getTournament());

        gameRepository.saveAndFlush(_game);

        return true;
    }

    @Override
    public boolean remove(Game game) {
        try{
            gameRepository.delete(game);
        } catch (Exception ex){
            return false;
        }


        return true;
    }

    @Override
    public boolean remove(long id) {
        try{
            if (id <= 0) return false;
            gameRepository.deleteById(id);
        } catch (Exception ex){
            return false;
        }


        return true;
    }

    @Override
    public Game getMatch(long id) {
        return gameRepository.findById(id).get();
    }

    @Override
    public List<Game> getMatches(long idUser, long idTournament, Date beforeInterval) {
        return gameRepository.findAllByTournament_IdAndTournament_User_Id(idTournament,idUser).stream()
                .filter(a -> a.getDate().compareTo(beforeInterval) >= 0).collect(Collectors.toList());
    }

    @Override
    public List<Game> getMatches(long idUser, long idTournament, Date beginInterval, Date endInterval) {
        return gameRepository.findAllByTournament_IdAndTournament_User_Id(idTournament,idUser)
                .stream().filter(a -> a.getDate().compareTo(beginInterval) >= 0 && a.getDate()
                        .compareTo(endInterval) < 0).collect(Collectors.toList());
    }

    @Override
    public List<Game> getMatches(long idUser, long idTournament, boolean state) {
        return gameRepository.findAllByTournament_IdAndTournament_User_IdAndState(idTournament, idUser, state);
    }

    @Override
    public List<Game> getMatches(long idUser, long idTournament, long idPlayer) {
        return gameRepository.findAllByTournament_IdAndTournament_User_Id(idTournament, idUser).stream()
                .filter(a -> a.getPlayers().stream().anyMatch(b -> b.getId() == idPlayer)).collect(Collectors.toList());
    }
}
