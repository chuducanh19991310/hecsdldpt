package com.btl.hecsdlpt.imageSearch.processing;



import com.btl.hecsdlpt.imageSearch.core.GrayImage;
import net.jafama.FastMath;

public class ComputerGradient {
    private final static float PI_FLOAT = (float) Math.PI;
    private final static float PI_OVER_TWO_FLOAT = (float) Math.PI / 2f;
    private final static float TWO_PI_FLOAT = (float) (Math.PI * 2);

    public static void gradientMagnitudesAndQuantisedOrientations(GrayImage image, GrayImage[] magnitudes) {
        final int numOriBins = magnitudes.length;

        for (int r = 0; r < image.height; r++) {
            for (int c = 0; c < image.width; c++) {
                float xgrad, ygrad;

                if (c == 0)
                    xgrad = 2.0f * (image.pixels[r][c + 1] - image.pixels[r][c]);
                else if (c == image.width - 1)
                    xgrad = 2.0f * (image.pixels[r][c] - image.pixels[r][c - 1]);
                else
                    xgrad = image.pixels[r][c + 1] - image.pixels[r][c - 1];
                if (r == 0)
                    ygrad = 2.0f * (image.pixels[r][c] - image.pixels[r + 1][c]);
                else if (r == image.height - 1)
                    ygrad = 2.0f * (image.pixels[r - 1][c] - image.pixels[r][c]);
                else
                    ygrad = image.pixels[r - 1][c] - image.pixels[r + 1][c];

                // mag là độ lớn
                // ori là hướng
                // ori + thêm pi/2 vì kết quả của hàm tan nằm từ -pi/2 đến pi/2
                // bản chất thì không thay đổi gì cả.
                 
                final float mag = (float) Math.sqrt(xgrad * xgrad + ygrad * ygrad);
                
                final float ori = mag == 0 ? PI_OVER_TWO_FLOAT
                        : (float) FastMath.atan(ygrad / xgrad)
                        + PI_OVER_TWO_FLOAT;
                
                float po = numOriBins * ori / PI_FLOAT;
                // cái po này dùng để tính xem nó thuộc bin nào
                final int oi = (int) Math.floor(po);
                // floor là làm tròn lên
                final float of = po - oi;
                // cái này chia phần xem nó vào bin ở bên trái bao nhiêu và bin ở bên phải bao nhiêu

                // reset
                for (int i = 0; i < magnitudes.length; i++)
                    magnitudes[i].pixels[r][c] = 0;

                // set
                magnitudes[oi % numOriBins].pixels[r][c] = (1f - of) * mag;
                magnitudes[(oi + 1) % numOriBins].pixels[r][c] = of * mag;
            }
        }
    }
}
