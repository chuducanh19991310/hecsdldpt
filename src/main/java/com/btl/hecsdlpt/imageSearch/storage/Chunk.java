package com.btl.hecsdlpt.imageSearch.storage;

import java.io.Serializable;

public class Chunk  implements Serializable {
    private static final long serialVersionUID = 1L;
    public String path;
    public double[] data;
    public Chunk() {}
    public Chunk(String path,
                 double[] data)
    {
        this.path = path;
        this.data = data;
    }
    @Override
    public String toString() {
        return path + data;
    }
}
