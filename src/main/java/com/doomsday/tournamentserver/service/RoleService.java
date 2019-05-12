package com.doomsday.tournamentserver.service;


import com.doomsday.tournamentserver.db.Role;
import com.doomsday.tournamentserver.db.User;

public interface RoleService {
    Role findRoleByName(String name);
    void createRole(String name);
    void deleteRole(String name);
    void addRoleToUser(Role role, User user);
    void removeRoleFromUser(Role role, User user);
}
