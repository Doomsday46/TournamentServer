package com.doomsday.tournamentserver.domain.tossup;

import com.doomsday.tournamentserver.domain.pair.PairNumberofPlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomTossUp implements TossUp{

    private Integer countPlayers;
    private List<Integer> numbersPlayers;

    public RandomTossUp(Integer countPlayers){
        if(countPlayers < 2 )  throw new IllegalArgumentException();
        this.countPlayers = countPlayers;
    }

    @Override
    public List<Integer> getPairsPlayers() {
        numbersPlayers = initListNumbersPlayers();
        shuffle();
        return numbersPlayers;
    }
        
    private List<Integer> initListNumbersPlayers(){
        List<Integer> numbers = new ArrayList<>();
        for(int i=1; i<=countPlayers; i++)
            numbers.add(i);
        return numbers;
    }
    private void shuffle(){
        Collections.shuffle(numbersPlayers);
    }
}
