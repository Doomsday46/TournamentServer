package com.doomsday.tournamentserver.db.repository;

import com.doomsday.tournamentserver.db.Setting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingRepository extends JpaRepository<Setting, Long> {

}
