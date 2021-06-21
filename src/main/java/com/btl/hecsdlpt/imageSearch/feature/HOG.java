package com.btl.hecsdlpt.imageSearch.feature;

import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import com.btl.hecsdlpt.imageSearch.analysis.GradientOrientationHistogramExtractor;
import com.btl.hecsdlpt.imageSearch.core.GrayImage;
import com.btl.hecsdlpt.imageSearch.core.Histogram;
import java.awt.image.FilteredImageSource;
import java.io.File;


public class HOG {
    public GradientOrientationHistogramExtractor analyser; // công cụ để phân tích ảnh
    public  GrayImage image;
    public HOG(int nbins) {
        analyser = new GradientOrientationHistogramExtractor(nbins);
        // bins ở đây là số điểm của hướng được hội tụ vào.
        // trong bài này thì dùng với số bins = 9, 0-20-40-..180
    }
    public void analyseImage(GrayImage image, GrayImage edge) throws Exception {
        // thực hiện việc tính toán hướng và góc độ của ảnh dựa theo đường biên.
        this.image = image;
        analyser.analyseImage(image, edge);
    }

    public Histogram extractFeature(int numBlockX, int numBlockY) {
        Integer blockSize = this.analyser.getNbins();
        float dx = image.width / numBlockX;
        float dy = image.height / numBlockY;
        // chia ảnh theo từng block và thống kê theo từng block
        Histogram tmp = new Histogram(blockSize);
        Histogram output = new Histogram(blockSize * numBlockX * numBlockY);
        Integer block = 0;
        
        for (float i = 0; i < image.width; i+= dx) {
            for (float j = 0; j < image.height; j += dy) {
                // duyệt tất cả các block
                this.analyser.computeHistogram((int) i, (int) j, (int) dx, (int) dy, tmp);
                // tính toán cho blog (i, i+dx; j+dy)
                tmp.normaliseL2();
                System.arraycopy(tmp.values, 0, output.values, blockSize * block, blockSize);
                // gép vào vector output
                block++;
            }
        }
        return output;
    }
}
