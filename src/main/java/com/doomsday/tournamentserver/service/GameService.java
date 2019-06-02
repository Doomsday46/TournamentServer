package com.doomsday.tournamentserver.service;

import com.doomsday.tournamentserver.database.Entity.Game;

import java.util.Date;
import java.util.List;

public interface GameService {
    boolean save(long idUser, long idTournament, Game game);
    boolean update(Game game);
    boolean remove(Game game);
    boolean remove(long id);
    Game getMatch(long id);
    List<Game> getMatches(long idUser, long idTournament, Date beforeInterval);
    List<Game> getMatches(long idUser, long idTournament, Date beginInterval, Date endInterval);
    List<Game> getMatches(long idUser, long idTournament, boolean state);
    List<Game> getMatches(long idUser, long idTournament, long idPlayer);
}
