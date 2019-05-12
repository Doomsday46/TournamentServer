package com.doomsday.tournamentserver.db.repository;

import com.doomsday.tournamentserver.db.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Location findByNameAndDescription(String name, String description);
    Location findAllByState(boolean state);
}
