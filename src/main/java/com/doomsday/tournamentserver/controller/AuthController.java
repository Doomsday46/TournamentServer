package com.doomsday.tournamentserver.controller;

import com.doomsday.tournamentserver.db.User;
import com.doomsday.tournamentserver.service.SecurityService;
import com.doomsday.tournamentserver.service.UserService;
import com.doomsday.tournamentserver.service.implement.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AuthController {

    private final UserService userService;
    private final SecurityService securityService;
    private final UserValidator userValidator;

    @Autowired
    public AuthController(UserService userService, SecurityService securityService, UserValidator userValidator) {
        this.userService = userService;
        this.securityService = securityService;
        this.userValidator = userValidator;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@RequestBody User user) {
        //userValidator.validate(user);

        userService.save(user);

        securityService.autologin(user.getUsername(), user.getPasswordConfirm());

        return "Successfull";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody User user) {

        if (userService.findByUsername(user.getUsername()) == null) new ResponseEntity<>(HttpStatus.BAD_REQUEST);;

        securityService.autologin(user.getUsername(), user.getPassword());

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
