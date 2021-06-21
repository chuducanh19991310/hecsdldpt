package com.btl.hecsdlpt.imageSearch.core;

public class Histogram {
    public double[] values;

    public Histogram(int nbin) {
        this.values = new double[nbin];
    }

    public void normaliseL2() {
        double sumsq = 0;

        for (int i = 0; i < values.length; i++)
            sumsq += values[i] * values[i];

        if (sumsq != 0)
            for (int i = 0; i < values.length; i++)
                values[i] /= Math.sqrt(sumsq);
    }
}
