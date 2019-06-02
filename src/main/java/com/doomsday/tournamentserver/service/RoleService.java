package com.doomsday.tournamentserver.service;


import com.doomsday.tournamentserver.database.Entity.Role;
import com.doomsday.tournamentserver.database.Entity.User;

public interface RoleService {
    Role findRoleByName(String name);
    void createRole(String name);
    void deleteRole(String name);
    void addRoleToUser(Role role, User user);
    void removeRoleFromUser(Role role, User user);
}
