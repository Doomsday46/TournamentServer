package com.doomsday.tournamentserver.domain.service;

import com.doomsday.tournamentserver.domain.model.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class DomainPlayerServiceTests {

        private PlayerService testSubject;
        private Player player1;
        private Player player2;
        private Player player3;
        private List<Player> playerList;

        @Before
        public void initTest()
        {
            testSubject = new DomainPlayerService();
            player1 = mock(Player.class);
            player2 = mock(Player.class);
            player3 = mock(Player.class);
            playerList = new ArrayList<>();
            playerList.add(player1);
            playerList.add(player2);
        }
        @Test()
        public void test_addPlayer()
        {
            testSubject.addPlayer(player1);
        }
        @Test(expected = NullPointerException.class)
        public void test_addPlayer_whenNullParameter_resultException() throws NullPointerException
        {
            testSubject.addPlayer(null);
        }
        @Test()
        public void test_addPlayers()
        {
            testSubject.addPlayers(playerList);
        }
        @Test(expected = NullPointerException.class)
        public void test_addPlayers_whenNullParameter()throws NullPointerException
        {
            testSubject.addPlayers(null);
        }
        @Test()
        public void test_getAllPlayers()
        {
            testSubject.addPlayer(player1);
            testSubject.addPlayer(player2);
            assertEquals(2,testSubject.getAllPlayers().size());
        }
        @Test()
        public void test_getAllPlayers_whenEmptyPlayersList()
        {
            assertEquals(0,testSubject.getAllPlayers().size());
        }
        @Test()
        public void test_getAllPlayersNumbers()
        {
            testSubject.addPlayer(player1);
            testSubject.addPlayer(player2);
            List<Integer> list = new ArrayList<>();
            list.add(1);
            list.add(2);
            assertEquals(list,testSubject.getAllPlayersNumbers());
        }
        @Test()
        public void test_getAllPlayersNumbers_whenEmptyPlayersList()
        {

            assertEquals(new ArrayList<>(),testSubject.getAllPlayersNumbers());
        }
        @Test(expected = IllegalArgumentException.class)
        public void test_addPlayer_whenDuplicateParameter_resultException() throws IllegalArgumentException
        {
            testSubject.addPlayer(player1);
            testSubject.addPlayer(player1);
        }
        @Test
        public void test_getPlayerNumber(){
            testSubject.addPlayer(player1);
            assertEquals((Integer)1,testSubject.getPlayerNumber(player1));
        }
        @Test(expected = IllegalArgumentException.class)
        public void test_getPlayerNumber_whenInvalidPlayer()throws IllegalArgumentException
        {
            testSubject.addPlayer(player1);
            assertEquals((Integer)1,testSubject.getPlayerNumber(player2));
        }
        @Test
        public void test_getPlayerByNumber(){
            testSubject.addPlayer(player1);
            assertEquals(player1,testSubject.getPlayerByNumber(1));
        }
        @Test(expected = IllegalArgumentException.class)
        public void test_getPlayerByNumber_whenInvalidPlayer()throws IllegalArgumentException
        {
            testSubject.addPlayer(player1);
            testSubject.getPlayerByNumber(3);
        }

}
