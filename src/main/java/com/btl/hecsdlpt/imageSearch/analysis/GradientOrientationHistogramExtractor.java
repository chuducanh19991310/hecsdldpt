package com.btl.hecsdlpt.imageSearch.analysis;

import com.btl.hecsdlpt.imageSearch.core.GrayImage;
import com.btl.hecsdlpt.imageSearch.core.Histogram;
import com.btl.hecsdlpt.imageSearch.processing.ComputerGradient;
import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.FImage;

import javax.swing.*;

public class GradientOrientationHistogramExtractor {
    private Integer nbins;
    private SummedAreaTable[] sats;
    // mảng cộng dồn độ lớn, mỗi mảng thì đại diện cho một hướng
    // ví dụ mảng sats[0] đại diện cho hướng 0 độ, sats[1] đại diện cho hướng 20 độ
    public  GradientOrientationHistogramExtractor(int nbins) {
        this.nbins = nbins;
        this.sats = new SummedAreaTable[nbins];
    }


    public  Integer getNbins() {
        return this.nbins;
    }

    protected void computeSATs(GrayImage[] magnitudeMaps) {
        for (int i = 0; i < nbins; i++) {
            // khởi tạo mảng cộng dồn
            // và tính toán các giá trị cần thiết.
            sats[i] = new SummedAreaTable(magnitudeMaps[i]);
        }
    }


    public void analyseImage(GrayImage image, GrayImage edge) throws Exception {
        final GrayImage[] magnitudes = new GrayImage[nbins];
        for (int i = 0; i < nbins; i++) {
            magnitudes[i] = new GrayImage(image.width, image.height);
        }
        ComputerGradient.gradientMagnitudesAndQuantisedOrientations(image, magnitudes);
        // tính toán và tích lũy độ lớn cho từng phương
        for (int i = 1; i < nbins; i++) {
            magnitudes[i].multiplyInPlace(edge);
            // loại bỏ đi những điểm không phải cực đại
        }
        JFrame[] frame = new JFrame[nbins+1];
//        for (int i = 0; i < nbins; i++) {
//            frame[i] = new JFrame();
//            frame[i].setLocation(200*i, 60);
//            frame[i].setVisible(true);
//            FImage img = new FImage(magnitudes[i].width, magnitudes[i].height);
//            img.pixels = magnitudes[i].pixels;
//            DisplayUtilities.display(img, frame[i]);
//        }
        computeSATs(magnitudes);
    }

    public void  computeHistogram(int x, int y, int w, int h, Histogram hist) {
        final int x2 = x + w;
        final int y2 = y + h;
        final double[] values = hist.values;

        for (int i = 0; i < values.length; i++) {
            // duyệt qua từng hướng, tính tổng tích lũy,
            // mỗi hướng tương ứng với một cột.
            final float val = sats[i].calculateArea(x, y, x2, y2);
            values[i] = Math.max(0, val);
        }
    }


}
