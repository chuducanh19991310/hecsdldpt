package com.btl.hecsdlpt.imageSearch.analysis;

import com.btl.hecsdlpt.imageSearch.core.GrayImage;



public class SummedAreaTable {

    public GrayImage data;

    public SummedAreaTable(GrayImage image) {
        computeTable(image);
    }


    public float calculateArea(int x1, int y1, int x2, int y2) {
        final float A = data.pixels[y1][x1];
        final float B = data.pixels[y1][x2];
        final float C = data.pixels[y2][x2];
        final float D = data.pixels[y2][x1];

        return A + C - B - D;
        // công thức tính tổng cộng dồn cho khoảng (x1, y1) đến (x2, y2)
        // Nó giống với việc để tính từ x-y thì tính tổng  từ 1->y trừ đi tổng từ 1->x-1
        // ở đây các tọa độ không phải -1 vì ở bước tính tổng cộng dồn đã 
        // thực hiện + 1 trước rồi
    }

    public void computeTable(GrayImage image) {
        data = new GrayImage(image.width + 1, image.height + 1);
        for (int y = 0; y < image.height; y++) {
            for (int x = 0; x < image.width; x++) {
                // +1 thì ở bước tính tổng cộng dồn sẽ không phải -1 nữa.
                data.pixels[y + 1][x + 1] = image.pixels[y][x] +
                        data.pixels[y + 1][x] +  data.pixels[y][x + 1] - data.pixels[y][x];
            }
        }
        // mảng data là mảng cộng dồn.
    }
}
