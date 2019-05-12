package com.doomsday.tournamentserver.service;


import com.doomsday.tournamentserver.db.User;

public interface SecurityService {
    String findLoggedInUsername();
    User findLoggedInUser();
    void autologin(String username, String password);
    void logout();
}
