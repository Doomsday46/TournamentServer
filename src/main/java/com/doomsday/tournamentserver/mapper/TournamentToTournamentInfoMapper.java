package com.doomsday.tournamentserver.mapper;

import com.doomsday.tournamentserver.db.Entity.Tournament;
import com.doomsday.tournamentserver.service.model.information.LocationViewInformation;
import com.doomsday.tournamentserver.service.model.information.PlayerViewInformation;
import com.doomsday.tournamentserver.service.model.information.SettingInformation;
import com.doomsday.tournamentserver.service.model.information.TournamentInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class TournamentToTournamentInfoMapper implements Mapper<TournamentInformation, Tournament> {

    private final PlayerToPlayerInfoMapper playerToPlayerInfoMapper;
    private final LocationToLocationInfoMapper locationToLocationInfoMapper;
    private final SettingToSettingInfoMapper settingToSettingInfoMapper;
    private final GameToMatchInformationMapper gameToMatchInformationMapper;
    private final PrizePlaceToPrizePlaceInfoMapper prizePlaceToPrizePlaceInfoMapper;

    @Autowired
    public TournamentToTournamentInfoMapper(PlayerToPlayerInfoMapper playerToPlayerInfoMapper, LocationToLocationInfoMapper locationToLocationInfoMapper, SettingToSettingInfoMapper settingToSettingInfoMapper, GameToMatchInformationMapper gameToMatchInformationMapper, PrizePlaceToPrizePlaceInfoMapper prizePlaceToPrizePlaceInfoMapper) {
        this.playerToPlayerInfoMapper = playerToPlayerInfoMapper;
        this.locationToLocationInfoMapper = locationToLocationInfoMapper;
        this.settingToSettingInfoMapper = settingToSettingInfoMapper;
        this.gameToMatchInformationMapper = gameToMatchInformationMapper;
        this.prizePlaceToPrizePlaceInfoMapper = prizePlaceToPrizePlaceInfoMapper;
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
        SettingInformation settingInfo = null;
        if (tournament.getSetting() != null) {

            settingInfo = settingToSettingInfoMapper.map(tournament.getSetting());

        }

        var isStarted = tournament.isStarted();
        var matches = tournament.getGames().stream().map(gameToMatchInformationMapper::map).collect(Collectors.toList());
        var prizePlaces = tournament.getPrizePlace().stream().map(prizePlaceToPrizePlaceInfoMapper::map).collect(Collectors.toList());

        return new TournamentInformation(tournament.getId(), tournament.getName(), settingInfo, object.finished, playerInfoList, locationInfoList, tournament.isStarted(), isStarted, matches, prizePlaces);
    }
}
