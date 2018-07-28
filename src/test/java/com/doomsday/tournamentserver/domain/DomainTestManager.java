package com.doomsday.tournamentserver.domain;

import com.doomsday.tournamentserver.domain.scheme.OlympicSchemeTests;
import com.doomsday.tournamentserver.domain.scheme.RoundSchemeTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        OlympicSchemeTests.class,
        RoundSchemeTests.class
})
public class DomainTestManager {
}
