package com.doomsday.tournamentserver.service;


import com.doomsday.tournamentserver.db.Entity.User;

public interface TemplateHelper {
    boolean isLoggedIn();
    boolean isAdmin();
    boolean isAdmin(User user);
    String getUsername();

    String removeSpaces(String fieldName);
}
