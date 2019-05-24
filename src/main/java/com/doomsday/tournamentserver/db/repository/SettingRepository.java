package com.doomsday.tournamentserver.db.repository;

import com.doomsday.tournamentserver.db.Setting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface SettingRepository extends JpaRepository<Setting, Long> {
    Setting findByUser_IdAndTournament_IdAndId(Long user_id, long tournament_id, long id);
    Setting findByUser_IdAndId(Long user_id, long id);
    Setting findByUser_IdAndTournament_Id(Long user_id, long tournament_id);
    Setting findByUser_IdAndIdAndTournament_Id(Long user_id, long id, long tournament_id);
}
