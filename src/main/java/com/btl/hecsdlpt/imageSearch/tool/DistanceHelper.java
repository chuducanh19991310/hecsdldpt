package com.btl.hecsdlpt.imageSearch.tool;

public class DistanceHelper {
    public int index;
    public Double distance;

    public DistanceHelper(int index, Double distance) {
        this.index = index;
        this.distance = distance;
    }

    public DistanceHelper() {
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
