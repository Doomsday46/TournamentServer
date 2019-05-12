package com.doomsday.tournamentserver.service.implement;

import com.doomsday.tournamentserver.db.Player;
import com.doomsday.tournamentserver.service.PlayerService;
import com.doomsday.tournamentserver.service.model.view.PlayerView;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class PlayerServiceImpl implements PlayerService {
    @Override
    public boolean addPlayerForTournament(long idUser, PlayerView player) {
        return false;
    }

    @Override
    public boolean updatePlayerInformation(long idUser, long idPlayer, PlayerView player) {
        return false;
    }

    @Override
    public boolean remove(long idUser, long idTournament, long idPlayer) {
        return false;
    }

    @Override
    public boolean remove(long idUser, Player player) {
        return false;
    }

    @Override
    public Player getPlayer(long idUser, long idTournament, long idPlayer) {
        return null;
    }

    @Override
    public List<Player> getPlayers(long idUser, long idTournament) {
        return null;
    }

    @Override
    public List<Player> getPlayers(long idUser, long idTournament, String firstName, String secondName) {
        return null;
    }

    @Override
    public List<Player> getPlayers(long idUser, long idTournament, String firstName) {
        return null;
    }

    @Override
    public List<Player> getPlayers(long idUser, long idTournament, Date birthDay) {
        return null;
    }

    @Override
    public List<Player> getPlayers(long idUser, long idTournament, int age) {
        return null;
    }
}
