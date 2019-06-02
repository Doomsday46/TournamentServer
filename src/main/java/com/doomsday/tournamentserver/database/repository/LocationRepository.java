package com.doomsday.tournamentserver.database.repository;

import com.doomsday.tournamentserver.database.Entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface LocationRepository extends JpaRepository<Location, Long> {
    Location findByNameAndDescription(String name, String description);
    Location findAllByState(boolean state);
    Location findByIdAndUser_Id(long id, Long user_id);
    Location findByNameAndDescriptionAndUser_Id(String nameLocation, String descriptionLocation, long idUser);
    Location findByIdAndUser_IdAndTournament_Id(long id, Long user_id, long tournament_id);
    List<Location> findAllByUser_IdAndTournament_Id(Long user_id, long tournament_id);
    List<Location> findAllByUser_IdAndStateAndTournament_Id(Long user_id, boolean state, long tournament_id);
    boolean removeByUser_IdAndTournament_IdAndId(Long user_id, long tournament_id, long id);

}
