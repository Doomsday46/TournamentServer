package com.doomsday.tournamentserver.database.repository;

import com.doomsday.tournamentserver.database.Entity.Setting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingRepository extends JpaRepository<Setting, Long> {
    Setting findByUser_IdAndTournament_IdAndId(Long user_id, long tournament_id, long id);
    Setting findByUser_IdAndId(Long user_id, long id);
    Setting findByUser_IdAndTournament_Id(Long user_id, long tournament_id);
    Setting findByUser_IdAndIdAndTournament_Id(Long user_id, long id, long tournament_id);
}
