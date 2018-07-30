package com.doomsday.tournamentserver.domain.scheme;

import com.doomsday.tournamentserver.domain.pair.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class OlympicSchemeTests {

    private Scheme olympicSchemeStandardSize, olympicSchemeNotStandartSize;

    @Before
    public void init() throws Exception {
        olympicSchemeStandardSize = new OlympicScheme(16);
        olympicSchemeNotStandartSize = new OlympicScheme(9);
    }

    @Test(expected = Exception.class)
    public void testInit_whenNegativeParameter_resultException() throws Exception {
        new OlympicScheme(-16);
    }

    @Test(expected = Exception.class)
    public void testInit_whenParameterLessThan2_resultException() throws Exception {
        new OlympicScheme(1);
    }

    @Test
    public void testInit_whenStandardSizePlayers() throws Exception {
        new OlympicScheme(16);
    }

    @Test
    public void testInit_whenNotStandardSizePlayers() throws Exception {
        new OlympicScheme(9);
    }

    @Test
    public void testGetNextNotPlayedPair_whenStandardSizePlayers_resultFirstPair() throws Exception {
        Pair<Integer, Integer> pairs = new Pair<>(1, 2);
        assertEquals(pairs, olympicSchemeStandardSize.getNextNotPlayedPair());
    }

    @Test
    public void testGetNextNotPlayedPair_whenStandardSizePlayers__resultSecondPair() throws Exception {
        Pair<Integer, Integer> pairs = new Pair<>(3, 4);
        olympicSchemeStandardSize.getNextNotPlayedPair();
        assertEquals(pairs, olympicSchemeStandardSize.getNextNotPlayedPair());
    }

    @Test
    public void testGetNextNotPlayedPair_whenStandardSizePlayers__resultNull() throws Exception {
        olympicSchemeStandardSize.getNextNotPlayedPair();
        olympicSchemeStandardSize.getNextNotPlayedPair();
        olympicSchemeStandardSize.getNextNotPlayedPair();
        olympicSchemeStandardSize.getNextNotPlayedPair();
        olympicSchemeStandardSize.getNextNotPlayedPair();
        olympicSchemeStandardSize.getNextNotPlayedPair();
        olympicSchemeStandardSize.getNextNotPlayedPair();
        olympicSchemeStandardSize.getNextNotPlayedPair();
        assertEquals(null, olympicSchemeStandardSize.getNextNotPlayedPair());
    }

    @Test
    public void testGetAllPairsInTour_whenStandardSizePlayers__resultFirstTour() throws Exception {
        List<Pair<Integer, Integer>> pairs = new ArrayList<>();
        pairs.add(new Pair<>(1, 2));
        pairs.add(new Pair<>(3, 4));
        pairs.add(new Pair<>(5, 6));
        pairs.add(new Pair<>(7, 8));
        pairs.add(new Pair<>(9, 10));
        pairs.add(new Pair<>(11, 12));
        pairs.add(new Pair<>(13, 14));
        pairs.add(new Pair<>(15, 16));
        assertEquals(pairs, olympicSchemeStandardSize.getAllPairsInTour(1));
    }

    @Test
    public void testGetAllPairsInTour_whenStandardSizePlayers__resultEmpty() throws Exception {
        assertEquals(4, olympicSchemeStandardSize.getAllPairsInTour(2).size());
    }

    @Test
    public void testGetAllPairsInTour_whenStandardSizePlayers__resultPairsOnSecondTour() throws Exception {
        List<Pair<Integer, Integer>> pairs = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(3);
        list.add(5);
        list.add(8);
        olympicSchemeStandardSize.updateScheme(list);
        pairs.add(new Pair<>(1, 3));
        pairs.add(new Pair<>(5, 8));
        pairs.add(new Pair<>(0, 0));
        pairs.add(new Pair<>(0, 0));
        assertEquals(pairs, olympicSchemeStandardSize.getAllPairsInTour(2));
    }

    @Test
    public void testGetAllPairs_whenNotStandardSizePlayers() {
        assertEquals(14, olympicSchemeNotStandartSize.getAllPairs().size());
    }

    @Test
    public void testGetNextNotPlayedPair_whenNotStandardSizePlayers_resultFirstPair() throws Exception {
        assertEquals(new Pair<>(1, 2), olympicSchemeNotStandartSize.getNextNotPlayedPair());
    }

    @Test
    public void testGetNextNotPlayedPair_whenNoStandardSizePlayers__resultSecondPair() throws Exception {
        Pair<Integer, Integer> pairs = new Pair<>(3, 4);
        olympicSchemeNotStandartSize.getNextNotPlayedPair();
        assertEquals(pairs, olympicSchemeNotStandartSize.getNextNotPlayedPair());
    }

    @Test
    public void testGetNextNotPlayedPair_whenNoStandardSizePlayers__resultNull() throws Exception {
        olympicSchemeNotStandartSize.getNextNotPlayedPair();
        olympicSchemeNotStandartSize.getNextNotPlayedPair();
        olympicSchemeNotStandartSize.getNextNotPlayedPair();
        olympicSchemeNotStandartSize.getNextNotPlayedPair();
        assertEquals(null, olympicSchemeNotStandartSize.getNextNotPlayedPair());
    }

    @Test
    public void testGetAllPairsInTour_whenNoStandardSizePlayers__resultFirstTour() throws Exception {
        List<Pair<Integer, Integer>> pairs = new ArrayList<>();
        pairs.add(new Pair<>(1, 2));
        pairs.add(new Pair<>(9, 0));
        pairs.add(new Pair<>(3, 4));
        pairs.add(new Pair<>(0, 0));
        pairs.add(new Pair<>(5, 6));
        pairs.add(new Pair<>(0, 0));
        pairs.add(new Pair<>(7, 8));
        pairs.add(new Pair<>(0, 0));
        assertEquals(pairs, olympicSchemeNotStandartSize.getAllPairsInTour(1));
    }

    @Test
    public void testGetAllPairsInTour_whenNoStandardSizePlayers__resultEmpty() throws Exception {
        assertEquals(4, olympicSchemeNotStandartSize.getAllPairsInTour(2).size());
    }

    @Test
    public void testGetAllPairsInTour_whenNoStandardSizePlayers__resultPairsOnSecondTour() throws Exception {
        List<Pair<Integer, Integer>> pairs = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(3);
        olympicSchemeNotStandartSize.updateScheme(list);
        list.add(1);
        olympicSchemeNotStandartSize.updateScheme(list);
        pairs.add(new Pair<>(1, 9));
        pairs.add(new Pair<>(3, 0));
        pairs.add(new Pair<>(0, 0));
        pairs.add(new Pair<>(0, 0));
        assertEquals(pairs, olympicSchemeNotStandartSize.getAllPairsInTour(2));
    }

    @Test
    public void testGetAllPairsInTour_whenNoStandardSizePlayers__resultPairsOnThirdTour() throws Exception {
        List<Pair<Integer, Integer>> pairs = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(3);
        olympicSchemeNotStandartSize.updateScheme(list);
        list.add(1);
        olympicSchemeNotStandartSize.updateScheme(list);
        list.add(1);
        olympicSchemeNotStandartSize.updateScheme(list);
        pairs.add(new Pair<>(1, 3));
        pairs.add(new Pair<>(0, 0));
        assertEquals(pairs, olympicSchemeNotStandartSize.getAllPairsInTour(3));
    }

    @Test
    public void testGetAllPairsInTour_whenNoStandardSizePlayers__resultPairOnThirdTour() throws Exception {
        List<Pair<Integer, Integer>> pairs = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        list.add(5);
        list.add(7);
        olympicSchemeNotStandartSize.updateScheme(list);
        pairs.add(new Pair<>(0, 0));
        pairs.add(new Pair<>(5, 7));
        assertEquals(pairs, olympicSchemeNotStandartSize.getAllPairsInTour(3));
    }
}
