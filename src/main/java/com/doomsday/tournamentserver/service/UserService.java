package com.doomsday.tournamentserver.service;


import com.doomsday.tournamentserver.database.Entity.User;

import java.util.List;

public interface UserService {
    void save(User user);
    void update(User user);
    void delete(User user);
    User findById(Long id);
    User findByUsername(String username);
    List<User> getAll();
}
