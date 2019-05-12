package com.doomsday.tournamentserver.domain.scheme;

import com.doomsday.tournamentserver.domain.exception.EmptyParameter;
import com.doomsday.tournamentserver.domain.pair.Pair;

import java.util.*;

public class RoundScheme implements Scheme {
    private List<List<Meet>> toursList;
    private Integer playersCount;

    public RoundScheme(Integer playersCount)
    {
        if (playersCount < 2) throw new IllegalArgumentException();
        this.toursList = this.buildScheme(playersCount);
        this.playersCount = playersCount;
    }

    private void assignMeet(Integer firstNumber, Integer secondNumber){
        for (List<Meet> tour: this.toursList)
        {
            for (Meet meet: tour) {
                Set<Integer> meetSet = new HashSet<>();
                meetSet.add(meet.getFirstNumber());
                meetSet.add(meet.getSecondNumber());
                Set<Integer> paramSet = new HashSet<>();
                paramSet.add(firstNumber);
                paramSet.add(secondNumber);
                if (meetSet.equals(paramSet)) {
                    meet.assign();
                    return;
                }
            }

        }
    }
    private List<List<Meet>> buildScheme(Integer playersCount)
    {
        List<List<Meet>> toursList = new ArrayList<>();
        for (int i = 0; i < playersCount - 1; i++) {
            ArrayList<Meet> tour = new ArrayList<>();
            for (int j = i; j < playersCount; j++) {
                if (i == j) continue;
                Meet meet = new Meet(i+1, j+1);
                tour.add(meet);
            }
            toursList.add(tour);
        }
        return toursList;
    }

    @Override
    public List<Pair<Integer, Integer>> getAllPairs() {
        List<Pair<Integer, Integer>> pairList = new ArrayList<>();
        for (List<Meet> tour: this.toursList)
        {
            for (Meet meet: tour) {

                pairList.add(new Pair<>(meet.firstNumber, meet.secondNumber));
            }
        }
        return pairList;
    }

    @Override
    public Pair<Integer, Integer> getNextNotPlayedPair(){
        for (List<Meet> tour: this.toursList)
        {
            for (Meet meet: tour) {
                if (!(meet.isAssigned())){
                    meet.assign();
                    return new Pair<>(meet.firstNumber, meet.secondNumber);
                }
            }
        }
        return null;
    }

    @Override
    public List<Pair<Integer, Integer>> getAllPairsInTour(Integer tourNumber) throws IllegalArgumentException {
        if (tourNumber == null) throw new NullPointerException();
        if (tourNumber > this.toursList.size()-1 || tourNumber < 0) throw new IllegalArgumentException();
        List<Pair<Integer, Integer>>  tour = new ArrayList<>();
        for (Meet meet: this.toursList.get(tourNumber))
        {
            tour.add(new Pair<>(meet.firstNumber, meet.secondNumber));
        }
        return tour;
    }

    @Override
    public Integer getMaxPairCount() {

        return (playersCount*(playersCount-1))/2;
    }

    @Override
    public Integer getToursCount() {
        return this.toursList.size();
    }

    @Override
    public void updateScheme(List<Integer> winnersList) throws EmptyParameter {

    }

    private List<Integer> collectNumbers()
    {
        Set<Integer> numbers = new HashSet<Integer>();
        for (List<Meet> tour: toursList)
        {
            for (Meet meet: tour)
            {
                numbers.add(meet.getFirstNumber());
                numbers.add(meet.getSecondNumber());
            }
        }
        List<Integer> result = new ArrayList<>(numbers);
        Collections.sort(result);
        return result;
    }

    private Boolean checkMeet(Integer firstNumber, Integer secondNumber)
    {
        Meet compareMeet = new Meet(firstNumber, secondNumber);
        for (List<Meet> tour: toursList)
        {
            for (Meet meet: tour)
            {
                if (meet.equals(compareMeet))
                {compareMeet = meet;
                    break;}
            }
        }
        return compareMeet.isAssigned();
    }

    @Override
    public String toString() {
        return "Круговая система";
    }


    private class Meet {
        private Integer firstNumber;
        private Integer secondNumber;
        private Boolean assigned;

        Boolean isAssigned() {
            return assigned;
        }

        Meet(Integer firstNumber, Integer secondNumber) throws IllegalArgumentException {
            if ((firstNumber == null) || (secondNumber == null))
                throw new NullPointerException();
            if (firstNumber.equals(secondNumber))
                throw new IllegalArgumentException("Duplicate numbers in one meet is not allowed");
            this.firstNumber = firstNumber;
            this.secondNumber = secondNumber;
            this.assigned = false;
        }

        Integer getFirstNumber() {
            return this.firstNumber;
        }

        Integer getSecondNumber() {
            return this.secondNumber;
        }

        void assign() throws IllegalArgumentException {
            if (this.isAssigned())
                throw new IllegalArgumentException("Meet is already assigned");
            this.assigned = true;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Meet meet = (Meet) o;

            Set<Integer> firstSet = new HashSet<>();
            firstSet.add(this.getFirstNumber());
            firstSet.add(this.getSecondNumber());
            Set<Integer> secondSet = new HashSet<>();
            secondSet.add(meet.getFirstNumber());
            secondSet.add(meet.getSecondNumber());
            return firstSet.equals(secondSet);
        }
    }
}
