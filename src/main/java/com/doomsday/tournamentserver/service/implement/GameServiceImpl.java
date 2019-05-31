package com.doomsday.tournamentserver.service.implement;

import com.doomsday.tournamentserver.db.Game;
import com.doomsday.tournamentserver.service.GameService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class GameServiceImpl implements GameService {

    @Override
    public boolean save(long idUser, long idTournament, Game game) {
        return false;
    }

    @Override
    public boolean update(long idUser, long idTournament, Game game) {
        return false;
    }

    @Override
    public boolean remove(long idUser, long idTournament, Game game) {
        return false;
    }

    @Override
    public boolean remove(long idUser, long idTournament, long id) {
        return false;
    }

    @Override
    public Game getMatch(long idUser, long idTournament, long id) {
        return null;
    }

    @Override
    public Game getMatch(long idUser, long idTournament, boolean state) {
        return null;
    }

    @Override
    public List<Game> getMatches(long idUser, long idTournament, Date beforeInterval) {
        return null;
    }

    @Override
    public List<Game> getMatches(long idUser, long idTournament, Date beginInterval, Date endInterval) {
        return null;
    }

    @Override
    public List<Game> getMatches(long idUser, long idTournament, boolean state) {
        return null;
    }

    @Override
    public List<Game> getMatches(long idUser, long idTournament, long idPlayer) {
        return null;
    }
}
