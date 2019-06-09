package com.doomsday.tournamentserver.domain.service;

import com.doomsday.tournamentserver.domain.model.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
            when(player1.getNumber()).thenReturn(1);
            player2 = mock(Player.class);
            when(player2.getNumber()).thenReturn(2);
            player3 = mock(Player.class);
            when(player3.getNumber()).thenReturn(3);
            playerList = new ArrayList<>();
            playerList.add(player1);
            playerList.add(player2);
        }
        @Test()
        public void test_addPlayer()
        {
            testSubject.addNewPlayer(player1);
        }
        @Test(expected = NullPointerException.class)
        public void test_addPlayer_whenNullParameter_resultException() throws NullPointerException
        {
            testSubject.addNewPlayer(null);
        }
        @Test()
        public void test_addPlayers()
        {
            testSubject.addNewPlayers(playerList);
        }
        @Test(expected = NullPointerException.class)
        public void test_addPlayers_whenNullParameter()throws NullPointerException
        {
            testSubject.addNewPlayers(null);
        }
        @Test()
        public void test_getAllPlayers()
        {
            testSubject.addNewPlayer(player1);
            testSubject.addNewPlayer(player2);
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
            testSubject.addNewPlayer(player1);
            testSubject.addNewPlayer(player2);
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
            testSubject.addNewPlayer(player1);
            testSubject.addNewPlayer(player1);
        }
        @Test
        public void test_getPlayerNumber(){
            testSubject.addNewPlayer(player1);
            assertEquals((Integer)1,testSubject.getPlayerNumber(player1));
        }
        @Test(expected = IllegalArgumentException.class)
        public void test_getPlayerNumber_whenInvalidPlayer()throws IllegalArgumentException
        {
            testSubject.addNewPlayer(player1);
            assertEquals((Integer)1,testSubject.getPlayerNumber(player2));
        }
        @Test
        public void test_getPlayerByNumber(){
            testSubject.addNewPlayer(player1);
            assertEquals(player1,testSubject.getPlayerByNumber(1));
        }
        @Test(expected = IllegalArgumentException.class)
        public void test_getPlayerByNumber_whenInvalidPlayer()throws IllegalArgumentException
        {
            testSubject.addNewPlayer(player1);
            testSubject.getPlayerByNumber(3);
        }

}
