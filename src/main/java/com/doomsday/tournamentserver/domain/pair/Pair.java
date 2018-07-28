package com.doomsday.tournamentserver.domain.pair;

public class  Pair<T1, T2> {
    private T1 firstObject;
    private T2 secondObject;

    public Pair(T1 firstObject, T2 secondObject) {
        this.firstObject = firstObject;
        this.secondObject = secondObject;
    }

    public T1 getFirstObject() {
        return firstObject;
    }

    public T2 getSecondObject() {
        return secondObject;
    }

    @Override
    public boolean equals(Object object){
        if(this == object) return true;
        if(object == null) return false;
        if (getClass() != object.getClass())
            return false;
        Pair other = (Pair) object;
        if (firstObject != other.firstObject)
            return false;
        return secondObject == other.secondObject;
    }
}
