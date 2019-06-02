package com.doomsday.tournamentserver.service;

import com.doomsday.tournamentserver.database.Entity.Player;
import com.doomsday.tournamentserver.service.model.view.PlayerView;

import java.util.Date;
import java.util.List;

public interface PlayerService {
    boolean savePlayer(long idUser, PlayerView player);
    boolean updatePlayerInformation(long idUser, long idPlayer, PlayerView player);
    boolean remove(long idUser, long idTournament, long idPlayer);
    boolean remove(long idUser, Player player);
    Player getPlayer(long idUser, long idTournament, long idPlayer);
    List<Player> getPlayers(long idUser, long idTournament);
    List<Player> getPlayers(long idUser, long idTournament, String firstName, String secondName);
    List<Player> getPlayers(long idUser, long idTournament, String firstName);
    List<Player> getPlayers(long idUser, long idTournament, Date birthDay);
    List<Player> getPlayers(long idUser, long idTournament, int age);

}
