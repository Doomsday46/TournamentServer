package com.doomsday.tournamentserver.mapper;

import com.doomsday.tournamentserver.db.Tournament;
import com.doomsday.tournamentserver.service.model.information.LocationViewInformation;
import com.doomsday.tournamentserver.service.model.information.PlayerViewInformation;
import com.doomsday.tournamentserver.service.model.information.TournamentInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TournamentToTournamentInfoMapper implements Mapper<TournamentInformation, Tournament> {

    private final PlayerToPlayerInfoMapper playerToPlayerInfoMapper;
    private final LocationToLocationInfoMapper locationToLocationInfoMapper;
    private final SettingToSettingInfoMapper settingToSettingInfoMapper;

    @Autowired
    public TournamentToTournamentInfoMapper(PlayerToPlayerInfoMapper playerToPlayerInfoMapper, LocationToLocationInfoMapper locationToLocationInfoMapper, SettingToSettingInfoMapper settingToSettingInfoMapper) {
        this.playerToPlayerInfoMapper = playerToPlayerInfoMapper;
        this.locationToLocationInfoMapper = locationToLocationInfoMapper;
        this.settingToSettingInfoMapper = settingToSettingInfoMapper;
    }

    @Override
    public TournamentInformation map(Tournament object) {
        if (!object.getClass().equals(Tournament.class)) throw new IllegalArgumentException("incorrect model for mapping");

        var tournament = (Tournament) object;

        var playerInfoList = new ArrayList<PlayerViewInformation>();
        var locationInfoList = new ArrayList<LocationViewInformation>();

        tournament.getPlayers().forEach(player -> {
            var playerInfo = playerToPlayerInfoMapper.map(player);
            if (!playerInfoList.contains(playerInfo)) playerInfoList.add(playerInfo);
         });

        tournament.getLocations().forEach(location -> {
            var locationInfo = locationToLocationInfoMapper.map(location);
            if (!locationInfoList.contains(locationInfo)) locationInfoList.add(locationInfo);
        });

        var settingInfo = settingToSettingInfoMapper.map(tournament.getSetting());

        return new TournamentInformation(tournament.getId(), tournament.getName(), settingInfo, object.finished, playerInfoList, locationInfoList);
    }
}
