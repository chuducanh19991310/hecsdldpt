package com.btl.hecsdlpt.imageSearch.core;

public class GrayImage {
    public float pixels[][];
    public Integer width, height;
    public  GrayImage(){
    }

    public GrayImage(float[][] pixels){
        this.height = pixels.length;
        this.width = pixels[0].length;
        this.pixels = pixels;
    }

    public  GrayImage(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new float[height][width];
    }

    public Boolean checkSameSize(final GrayImage img) {
        return  (int)img.height == (int) this.height && (int)img.width == (int) this.width;
    }

    public GrayImage multiplyInPlace(final GrayImage img) throws Exception {
        if (!this.checkSameSize(img)) {
            throw new Exception("Not same size");
        }
        for (int i = 0; i < this.height; i++) {
            for (int j = 1; j < this.width; j++) {
                this.pixels[i][j] *= img.pixels[i][j];
            }
        }
        return this;
    }
}
