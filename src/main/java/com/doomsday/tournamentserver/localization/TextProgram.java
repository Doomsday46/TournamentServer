package com.doomsday.tournamentserver.localization;

import java.util.Locale;
import java.util.ResourceBundle;

public class  TextProgram {

    private ResourceBundle resourceBundle;

    public  TextProgram(String resource, Locale locale){
        this.resourceBundle = ResourceBundle.getBundle(resource,locale);
    }

    public  TextProgram(String resource){
        this.resourceBundle = ResourceBundle.getBundle(resource);
    }

    public ResourceBundle getResourceBundle() {
        return this.resourceBundle;
    }

}
