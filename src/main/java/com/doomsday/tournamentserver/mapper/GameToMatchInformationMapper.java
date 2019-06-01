package com.doomsday.tournamentserver.mapper;

import com.doomsday.tournamentserver.db.Entity.Game;
import com.doomsday.tournamentserver.service.model.information.MatchInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class GameToMatchInformationMapper implements Mapper<MatchInformation, Game> {

    private PlayerToPlayerInfoMapper playerToPlayerInfoMapper;
    private LocationToLocationInfoMapper locationToLocationInfoMapper;

    @Autowired
    public GameToMatchInformationMapper(PlayerToPlayerInfoMapper playerToPlayerInfoMapper, LocationToLocationInfoMapper locationToLocationInfoMapper) {
        this.playerToPlayerInfoMapper = playerToPlayerInfoMapper;
        this.locationToLocationInfoMapper = locationToLocationInfoMapper;
    }

    @Override
    public MatchInformation map(Game object) {

        var location  = locationToLocationInfoMapper.map(object.getLocation());
        var players = object.getPlayers().stream().map(a -> playerToPlayerInfoMapper.map(a)).collect(Collectors.toList());

        var matchInformation = new MatchInformation(object.getTournament().getId(),
                object.getId(), players, object.isState(), object.getDate(), location);

        return matchInformation;
    }
}
