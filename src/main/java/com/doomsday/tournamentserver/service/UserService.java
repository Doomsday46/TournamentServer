package com.doomsday.tournamentserver.service;
import java.util.List;
import com.doomsday.tournamentserver.database.entity.User;


public interface UserService {
    void save(User user);
    void update(User user);
    void delete(User user);
    User findById(Long id);
    User findByUsername(String username);
    List<User> getAll();
}
