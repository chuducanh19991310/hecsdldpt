package com.btl.hecsdlpt.imageSearch.tool;

import java.util.*;

public class VectorTool {
    public static Double getDistance(List<Double> v1, List<Double> v2) {
        if (v1.size() != v2.size()) {
            return 0.0;
        }
        Double distanceSquare = 0.0;
        int n = v1.size();
        for (int i = 0; i < n; i++) {
            final Double c =  v1.get(i) - v2.get(i);
            distanceSquare += c*c;
        }
        return  distanceSquare;
    }


    /**
     * Return array with index of vector after sort
     * @param target
     * @param arr
     * @return
     */
    public static List<Integer> getSortedIndex (List<Double> target, List<List<Double>> arr) {
        int n =  arr.size();
        List<DistanceHelper> orderByDistance = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            DistanceHelper distanceHelper = new DistanceHelper();
            distanceHelper.setIndex(i);
            distanceHelper.setDistance(VectorTool.getDistance(arr.get(i), target));
            orderByDistance.add(distanceHelper);
        }
        Collections.sort(orderByDistance, new Comparator<DistanceHelper>() {
            @Override
            public int compare(DistanceHelper o1, DistanceHelper o2) {
                if (o1.getDistance() >  o2.getDistance()) {
                    return 1;
                }
                if (o1.getDistance() < o2.getDistance()) {
                    return -1;

                }
                return 0;
            }
        });
        List<Integer> result = Arrays.asList(new Integer[n]);
        for (int i = 0; i < n; i++) {
            result.set(i, orderByDistance.get(i).getIndex());
        }
        return result;
    }
}
