package com.doomsday.tournamentserver.service.implement;

import com.doomsday.tournamentserver.db.Match;
import com.doomsday.tournamentserver.service.MatchService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class MatchServiceImpl implements MatchService {
    @Override
    public boolean save(long idUser, long idTournament, Match match) {
        return false;
    }

    @Override
    public boolean update(long idUser, long idTournament, Match match) {
        return false;
    }

    @Override
    public boolean remove(long idUser, long idTournament, Match match) {
        return false;
    }

    @Override
    public boolean remove(long idUser, long idTournament, long id) {
        return false;
    }

    @Override
    public Match getMatch(long idUser, long idTournament, long id) {
        return null;
    }

    @Override
    public Match getMatch(long idUser, long idTournament, boolean state) {
        return null;
    }

    @Override
    public List<Match> getMatches(long idUser, long idTournament, Date beforeInterval) {
        return null;
    }

    @Override
    public List<Match> getMatches(long idUser, long idTournament, Date beginInterval, Date endInterval) {
        return null;
    }

    @Override
    public List<Match> getMatches(long idUser, long idTournament, boolean state) {
        return null;
    }

    @Override
    public List<Match> getMatches(long idUser, long idTournament, long idPlayer) {
        return null;
    }
}
