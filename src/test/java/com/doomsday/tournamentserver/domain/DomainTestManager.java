package com.doomsday.tournamentserver.domain;

import com.doomsday.tournamentserver.domain.scheme.LocationServiceTests;
import com.doomsday.tournamentserver.domain.scheme.OlympicSchemeTests;
import com.doomsday.tournamentserver.domain.scheme.RoundSchemeTests;
import com.doomsday.tournamentserver.service.LocationService;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        OlympicSchemeTests.class,
        RoundSchemeTests.class,
        LocationServiceTests.class
})
public class DomainTestManager {
}
