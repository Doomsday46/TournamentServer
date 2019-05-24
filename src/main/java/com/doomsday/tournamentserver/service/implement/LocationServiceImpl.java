package com.doomsday.tournamentserver.service.implement;

import com.doomsday.tournamentserver.db.Location;
import com.doomsday.tournamentserver.db.repository.LocationRepository;
import com.doomsday.tournamentserver.db.repository.TournamentRepository;
import com.doomsday.tournamentserver.db.repository.UserRepository;
import com.doomsday.tournamentserver.mapper.LocationViewToLocationMapper;
import com.doomsday.tournamentserver.service.LocationService;
import com.doomsday.tournamentserver.service.model.view.LocationView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LocationServiceImpl implements LocationService {

    private LocationRepository locationRepository;
    private UserRepository userRepository;
    private TournamentRepository tournamentRepository;
    private LocationViewToLocationMapper locationViewToLocationMapper;


    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository, UserRepository userRepository, TournamentRepository tournamentRepository, LocationViewToLocationMapper locationViewToLocationMapper) {
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
        this.tournamentRepository = tournamentRepository;
        this.locationViewToLocationMapper = locationViewToLocationMapper;
    }

    @Override
    public boolean save(long idUser, LocationView location) {
        if (location == null)  throw new NullPointerException("No data available");

        try {
            var _location = locationViewToLocationMapper.map(location);
            if (tournamentRepository.existsById(location.getIdTournament())) {
                var tournament = tournamentRepository.findByIdAndUser_Id(location.getIdTournament(), idUser);
                var user = userRepository.getOne(idUser);
                _location.setTournament(tournament);
                _location.setUser(user);
                locationRepository.saveAndFlush(_location);
            } else return false;
        } catch (Exception ex) {
            return false;
        }


        return false;
    }

    @Override
    public boolean update(long idUser, long idLocation, LocationView locationView) {
        if (locationView == null)  throw new NullPointerException("No data available");
        if (!locationRepository.existsById(idLocation)) throw new NullPointerException("Location is missing in the database");

        try {
            var _newLocation = locationViewToLocationMapper.map(locationView);
            var _oldLocation = locationRepository.findByIdAndUser_Id(idLocation, idUser);

            _oldLocation.setName(_newLocation.getName());
            _oldLocation.setDescription(_newLocation.getDescription());

            locationRepository.saveAndFlush(_oldLocation);

        } catch (Exception ex) {
            return false;
        }


        return false;
    }

    @Override
    public boolean remove(long idUser, LocationView location) {
        if (location == null)  throw new NullPointerException("No data available");

        try {
            var _location = locationRepository.findByNameAndDescriptionAndUser_Id(location.getNameLocation(), location.getDescriptionLocation(), idUser);

            if ( _location != null) {
                locationRepository.delete(_location);
            } else {
                return false;
            }

        } catch (Exception ex) {
            return false;
        }

        return false;
    }

    @Override
    public boolean remove(long idUser, long id) {
        if (id > 0)  throw new NullPointerException("No data available");

        try {
            var _location = locationRepository.findByIdAndUser_Id(id, idUser);

            if (_location != null) {
                locationRepository.delete(_location);
            } else {
                return false;
            }

        } catch (Exception ex) {
            return false;
        }

        return false;
    }

    @Override
    public Location getLocation(long idUser, long idTournament, long idLocation) {
        return locationRepository.findByIdAndUser_IdAndTournament_Id(idLocation, idUser, idTournament);
    }

    @Override
    public Location getLocation(long idUser, long idLocation) {
        return locationRepository.findByIdAndUser_Id(idLocation, idUser);
    }

    @Override
    public List<Location> getLocations(long idUser, long idTournament) {
        return locationRepository.findAllByUser_IdAndTournament_Id(idUser, idTournament);
    }

    @Override
    public List<Location> getLocations(long idUser, long idTournament, boolean state) {
        return locationRepository.findAllByUser_IdAndStateAndTournament_Id(idUser, state, idTournament);
    }

    @Override
    public boolean remove(long idUser, long idTournament, long idLocation) {
        return locationRepository.removeByUser_IdAndTournament_IdAndId(idUser, idTournament, idLocation);
    }
}
