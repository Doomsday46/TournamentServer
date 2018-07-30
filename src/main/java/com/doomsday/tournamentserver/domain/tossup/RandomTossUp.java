package com.doomsday.tournamentserver.domain.tossup;

import com.doomsday.tournamentserver.domain.pair.PairNumberofPlayer;
import com.doomsday.tournamentserver.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RandomTossUp implements TossUp{

    private Integer countPlayers;
    private List<Integer> numbersPlayers;

    public RandomTossUp(Integer countPlayers){
        if(countPlayers < 2 ) throw new IllegalArgumentException();
        this.countPlayers = countPlayers;
    }

    @Override
    public List<PairNumberofPlayer> getPairsPlayers() {

        return null;
    }
}
