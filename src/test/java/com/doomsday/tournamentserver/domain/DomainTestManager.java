package com.doomsday.tournamentserver.domain;

import com.doomsday.tournamentserver.domain.scheme.OlympicSchemeTests;
import com.doomsday.tournamentserver.domain.scheme.RoundSchemeTests;
import com.doomsday.tournamentserver.domain.service.DomainDateServiceTests;
import com.doomsday.tournamentserver.domain.service.DomainLocationServiceTests;
import com.doomsday.tournamentserver.domain.service.DomainPlayerServiceTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        OlympicSchemeTests.class,
        RoundSchemeTests.class,
        DomainLocationServiceTests.class,
        DomainDateServiceTests.class,
        DomainPlayerServiceTests.class
})
public class DomainTestManager {
}
