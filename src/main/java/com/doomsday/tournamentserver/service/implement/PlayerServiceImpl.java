package com.doomsday.tournamentserver.service.implement;

import com.doomsday.tournamentserver.db.Player;
import com.doomsday.tournamentserver.db.repository.PlayerRepository;
import com.doomsday.tournamentserver.db.repository.TournamentRepository;
import com.doomsday.tournamentserver.db.repository.UserRepository;
import com.doomsday.tournamentserver.mapper.PlayerViewToPlayerMapper;
import com.doomsday.tournamentserver.service.PlayerService;
import com.doomsday.tournamentserver.service.model.view.PlayerView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
@Service
public class PlayerServiceImpl implements PlayerService {

    private PlayerRepository playerRepository;
    private TournamentRepository tournamentRepository;
    private UserRepository userRepository;
    private PlayerViewToPlayerMapper playerViewToPlayerMapper;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, TournamentRepository tournamentRepository, UserRepository userRepository, PlayerViewToPlayerMapper playerViewToPlayerMapper) {
        this.playerRepository = playerRepository;
        this.tournamentRepository = tournamentRepository;
        this.userRepository = userRepository;
        this.playerViewToPlayerMapper = playerViewToPlayerMapper;
    }

    @Override
    public boolean savePlayer(long idUser, PlayerView player) {
        if (idUser <= 0 || player == null || player.getIdTournament() <= 0)
            throw new NullPointerException("Incorrect parameter");

        try {
            var _player = playerViewToPlayerMapper.map(player);
            var tournament = tournamentRepository.findByIdAndUser_Id(player.getIdTournament(), idUser);
            var user = userRepository.getOne(idUser);

            _player.setUser(user);
            _player.setTournament(tournament);

            playerRepository.saveAndFlush(_player);
        } catch(Exception ex) {
            return false;
        }

        return true;
    }

    @Override
    public boolean updatePlayerInformation(long idUser, long idPlayer, PlayerView player) {
        if (idUser <= 0 || idPlayer  <= 0 ||  player == null || player.getIdTournament() <= 0)
            throw new NullPointerException("Incorrect parameter");

        try {
            var _player = playerViewToPlayerMapper.map(player);
            var oldInfoPlayer = playerRepository.findByIdAndUser_Id(idPlayer, idUser);

            oldInfoPlayer.setAge(_player.getAge());
            oldInfoPlayer.setFirstName(_player.getFirstName());
            oldInfoPlayer.setSurname(_player.getSurname());

            playerRepository.saveAndFlush(_player);
        } catch(Exception ex) {
            return false;
        }

        return true;
    }

    @Override
    public boolean remove(long idUser, long idTournament, long idPlayer) {
        if (idUser <= 0 || idTournament <= 0 || idPlayer <= 0)
            throw new NullPointerException("Incorrect parameter");

        try {
            var player = playerRepository.findByIdAndUser_IdAndTournament_Id(idPlayer, idUser, idTournament);

            playerRepository.delete(player);
        } catch(Exception ex) {
            return false;
        }

        return true;
    }

    @Override
    public boolean remove(long idUser, Player player) {
        if (idUser <= 0 || player == null || player.getId() <= 0)
            throw new NullPointerException("Incorrect parameter");

        try {
            var _player = playerRepository.findByIdAndUser_Id(player.getId(), idUser);
            playerRepository.delete(player);
        } catch(Exception ex) {
            return false;
        }

        return true;
    }

    @Override
    public Player getPlayer(long idUser, long idTournament, long idPlayer) {
        return playerRepository.findByIdAndUser_IdAndTournament_Id(idPlayer, idUser, idTournament);
    }

    @Override
    public List<Player> getPlayers(long idUser, long idTournament) {
        return playerRepository.findAllByUser_IdAndTournament_Id(idUser, idTournament);
    }

    @Override
    public List<Player> getPlayers(long idUser, long idTournament, String firstName, String secondName) {
        return playerRepository.findAllByUser_IdAndTournament_IdAndFirstNameAndSurname(idUser, idTournament, firstName, secondName);
    }

    @Override
    public List<Player> getPlayers(long idUser, long idTournament, String firstName) {
        return playerRepository.findAllByUser_IdAndTournament_IdAndFirstName(idUser, idTournament, firstName);
    }

    @Override
    public List<Player> getPlayers(long idUser, long idTournament, Date birthDay) {
        return playerRepository.findAllByUser_IdAndTournament_IdAndAge(idUser, idTournament, birthDay);
    }

    @Override
    public List<Player> getPlayers(long idUser, long idTournament, int age) {
        var _age = new Date(LocalDate.now().getYear() - age,1,1);
        return playerRepository.findAllByUser_IdAndTournament_IdAndAge(idUser, idTournament, _age);
    }
}
