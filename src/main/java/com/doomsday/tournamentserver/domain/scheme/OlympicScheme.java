package com.doomsday.tournamentserver.domain.scheme;

import com.doomsday.tournamentserver.domain.pair.Pair;
import com.doomsday.tournamentserver.exception.EmptyParameter;
import jdk.nashorn.internal.ir.EmptyNode;
import org.w3c.dom.ranges.RangeException;

import java.util.ArrayList;
import java.util.List;

public class OlympicScheme implements Scheme {
    private int countRounds;


    private static class Node {
        public Integer data;
        public Node leftPlayer, rightPlayer, nextPositionPlayer;
    }

    private Node root;
    private List<Node> par;
    private List<Node> children;
    private Integer countPlayers;
    private List<Pair<Integer, Integer>> givenPairsPlayers;

    public OlympicScheme(Integer countPlayers) throws Exception {
        if (countPlayers < 2) throw new Exception("Incorrect number of players");
        this.root = new Node();
        this.root.nextPositionPlayer = this.root;
        this.countRounds = countTour(countPlayers);
        this.countPlayers = countPlayers;
        this.givenPairsPlayers = new ArrayList<>();
        buildScheme(countRounds);
        fillFirstRound();
    }

    private void buildScheme(int level) {
        this.par = new ArrayList<>();
        this.children = new ArrayList<>();
        this.par.add(this.root);
        for (int i = 0; i < level; i++) {
            for (Node parent : this.par) {
                parent.leftPlayer = new Node();
                parent.leftPlayer.nextPositionPlayer = parent;
                this.children.add(parent.leftPlayer);
                parent.rightPlayer = new Node();
                parent.rightPlayer.nextPositionPlayer = parent;
                this.children.add(parent.rightPlayer);
            }
            this.par = new ArrayList<Node>(this.children);
            if (level != i + 1) this.children.clear();
        }
    }

    private int countTour(int countPlayers) {
        int count = 0, st = 2, n = 0;
        do {
            n++;
            count = (int) Math.pow(st, n);
        } while (count < countPlayers);
        return n;
    }

    private void fillFirstRound() {
        if (this.countPlayers == Math.pow(2, countTour(this.countPlayers)))
            fillFirstTourStandardCountPlayer();
        else {
            fillFirstTourNotStandardCountPlayer();
        }
    }

    private void fillFirstTourNotStandardCountPlayer() {
        int count = 0;
        for (int i = 0, j = 0; j < this.children.size(); i += 2, j += 4) {
            this.children.get(j).data = i + 1;
            this.children.get(j + 1).data = i + 2;
            count++;
        }
        for (int i = count * 2, j = 2; j < this.children.size() && i < this.countPlayers; i++, j += 4) {
            this.children.get(j).data = i + 1;
            if (j + 4 > this.children.size() && i + 1 < this.countPlayers) {
                this.children.get((j + 1 < this.children.size()) ? j + 1 : j).data = i + 2;
                return;
            }
            addPlayersNextTour(this.children.get(j));
        }
    }

    private void fillFirstTourStandardCountPlayer() {
        for (int i = 0; i < this.countPlayers; i++) {
            this.children.get(i).data = i + 1;
        }
    }

    private Node addPlayersNextTour(Node node) {
        if (node.nextPositionPlayer.rightPlayer.data == null || node.nextPositionPlayer.leftPlayer.data == null) {
            if (!checkLackPartner(node.nextPositionPlayer) || checkPartner(node.nextPositionPlayer)) {
                if (node.nextPositionPlayer.data == null) {
                    node.nextPositionPlayer.data = node.data;
                    return node.nextPositionPlayer;
                }
            }
        }
        return node;
    }

    private boolean checkPartner(Node node) {
        return node.rightPlayer.rightPlayer.data == null && node.rightPlayer.leftPlayer.data == null && (node.leftPlayer.rightPlayer.data != null || node.leftPlayer.leftPlayer.data != null);
    }

    private boolean checkLackPartner(Node node) {
        return node.rightPlayer.rightPlayer != null && node.rightPlayer.leftPlayer != null || node.leftPlayer.rightPlayer != null && node.leftPlayer.leftPlayer != null;
    }

    private void createSchedule(List<Integer> winnerList, List<Node> parents) {
        for (int i = 0; i < winnerList.size(); i++) {
            if (!addInScheme(parents, winnerList.get(i))) {
                List<Node> p = initParents(parents);
                if (p.size() <= 1) return;
                createSchedule(winnerList, initParents(parents));
            }
        }
    }

    private ArrayList<Node> initParents(List<Node> child) {
        ArrayList<Node> arrayList = new ArrayList<>();
        for (int i = 0; i < child.size(); i += 2) {
            arrayList.add(child.get(i).nextPositionPlayer);
        }
        return arrayList;
    }

    private boolean addInScheme(List<Node> child, Integer winner) {
        for (Node aChild : child) {
            if (aChild.data != null) {
                if (aChild.data.equals(winner)) {
                    if (aChild.nextPositionPlayer.data == null) {
                        aChild.nextPositionPlayer.data = winner;
                        addPlayersNextTour(aChild.nextPositionPlayer);
                    } else return false;
                    return true;
                }
            }
        }
        return false;
    }

    private Node finedTour(Node node) {
        if (node.nextPositionPlayer.rightPlayer.data != null && node.nextPositionPlayer.leftPlayer.data != null) {
            if (node.nextPositionPlayer.data == null) return node.nextPositionPlayer;
        }
        return null;
    }

    private List<Pair<Integer, Integer>> getTheRound(int round) {
        List<Pair<Integer, Integer>> arrayPair = new ArrayList<>();
        List<Node> parents = new ArrayList<>(this.children);
        parents = getRound(parents, --round);
        for (int i = 0; i < parents.size(); i += 2) {
            if (parents.get(i).nextPositionPlayer.leftPlayer.data == null && parents.get(i).nextPositionPlayer.rightPlayer.data == null)
                arrayPair.add(new Pair<>(0, 0));
            else if (parents.get(i).nextPositionPlayer.leftPlayer.data == null || parents.get(i).nextPositionPlayer.rightPlayer.data == null) {
                if (parents.get(i).nextPositionPlayer.leftPlayer.data == null)
                    arrayPair.add(new Pair<>(0, parents.get(i).nextPositionPlayer.rightPlayer.data));
                if (parents.get(i).nextPositionPlayer.rightPlayer.data == null)
                    arrayPair.add(new Pair<>(parents.get(i).nextPositionPlayer.leftPlayer.data, 0));
            } else {
                arrayPair.add(new Pair<>(parents.get(i).nextPositionPlayer.leftPlayer.data, parents.get(i).nextPositionPlayer.rightPlayer.data));
            }
        }
        return arrayPair;
    }

    private List<Node> getRound(List<Node> children, int round) {
        List<Node> parents = new ArrayList<>(children);
        children.clear();
        for (int i = 0; i < parents.size(); i += 2) {
            children.add(parents.get(i).nextPositionPlayer);
        }
        if (round == 0) return parents;
        else return getRound(initParents(parents), --round);
    }

    private Pair<Integer, Integer> getNextPair(List<Node> children) {
        for (Node aChild : children) {
            if (finedTour(aChild) != null) {
                Pair<Integer, Integer> pair = new Pair<>(aChild.nextPositionPlayer.leftPlayer.data, aChild.nextPositionPlayer.rightPlayer.data);
                if (!isPairInList(pair)) return pair;
            }
        }
        return (initParents(children).size() <= 1) ? null : getNextPair(initParents(children));
    }

    private void addPairsInList(List<Pair<Integer, Integer>> pairList) {
        for (Pair<Integer, Integer> pair : pairList) {
            if (!this.givenPairsPlayers.contains(pair)) {
                this.givenPairsPlayers.add(pair);
            }
        }
    }

    private void addPairInList(Pair<Integer, Integer> pair) {
        if (!this.givenPairsPlayers.contains(pair)) this.givenPairsPlayers.add(pair);
    }

    private boolean isPairInList(Pair<Integer, Integer> pair) {
        return this.givenPairsPlayers.contains(pair);
    }

    @Override
    public List<Pair<Integer, Integer>> getAllPairs() {
        int rounds = 1;
        List<Pair<Integer, Integer>> pairList = new ArrayList<>();
        while (rounds < this.countRounds) {
            try {
                pairList.addAll(getTheRound(rounds));
            } catch (Exception e) {
                e.printStackTrace();
            }
            rounds++;
        }
        return pairList;
    }

    @Override
    public Pair<Integer, Integer> getNextNotPlayedPair() {
        Pair<Integer, Integer> pair = getNextPair(this.children);
        if (isPairInList(pair)) return null;
        else {
            addPairInList(pair);
            return pair;
        }
    }

    @Override
    public List<Pair<Integer, Integer>> getAllPairsInTour(Integer tourNumber) throws IllegalArgumentException {
        if (tourNumber < 0 || tourNumber > this.countRounds) throw new IllegalArgumentException("Tour out of range");
        List<Pair<Integer, Integer>> list = getTheRound(tourNumber);
        return list;
    }

    @Override
    public Integer getMaxPairCount() {
        return countPlayers - 1;
    }

    @Override
    public Integer getToursCount() {
        return this.countRounds;
    }

    @Override
    public void updateScheme(List<Integer> winnersList) throws EmptyParameter {
        if (winnersList == null) throw new NullPointerException();
        if (winnersList.isEmpty()) throw new EmptyParameter("Empty list winner");
        createSchedule(winnersList, this.children);
    }

    @Override
    public String toString() {
        return "Олимпийская система";
    }
}
