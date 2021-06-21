package com.btl.hecsdlpt.imageSearch;

import com.btl.hecsdlpt.imageSearch.core.GrayImage;
import com.btl.hecsdlpt.imageSearch.feature.HOG;
import com.btl.hecsdlpt.imageSearch.storage.Chunk;
import com.btl.hecsdlpt.imageSearch.storage.StorageEngine;
import com.btl.hecsdlpt.imageSearch.tool.VectorTool;
import java.io.BufferedWriter;
import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.processing.edges.CannyEdgeDetector;
import org.openimaj.image.processing.edges.EdgeFinder;
import org.openimaj.image.processing.resize.ResizeProcessor;

public class FindImageSimilarApp {

    public double[] getFeature(String path) throws Exception {
        FImage fImagePre = ImageUtilities.readF(new File(path));
        FImage edgePre = ImageUtilities.readF(new File(path));
        
        edgePre.processInplace(new CannyEdgeDetector(0.02f, 0.05f, 2f));
        
        GrayImage image = new GrayImage(fImagePre.pixels);
        GrayImage edge = new GrayImage(edgePre.pixels);


        HOG hog = new HOG(9);
        hog.analyseImage(image, edge);
        double[] result =  hog.extractFeature(
            10, 10
        ).values;
        System.out.println(path);
//        debugVector(path, result);
        return result;
    }
    private String debugFile = "src/main/debug";
    
    public void debugVector(String path, double[] feature) {
        String result = path + System.lineSeparator();
        for (int i = 0; i < feature.length; i++) {
            result = result + feature[i] + " ";
        }
        try(FileWriter fw = new FileWriter(debugFile, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw))
        {
            out.printf(result + System.lineSeparator());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Double[] getDoubleArrayObject(double[] data) {
        Double[] result = new Double[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = data[i];
        }
        return result;
    }





    public void addToDataSet(String folderPath) throws Exception {
        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();
        List<Chunk> chunkList = new ArrayList<>();
        for (int i = 0; i < listOfFiles.length; i++) {
            double[] data = this.getFeature(listOfFiles[i].getAbsolutePath());
            chunkList.add(
                    new Chunk(listOfFiles[i].getPath(), data)
            );
        }
        Chunk[] data = chunkList.toArray(Chunk[]::new);
        StorageEngine.write(data);
    }

    public String[] getSimilar(String path) throws Exception {
        List<Double> target = Arrays.asList(this.getDoubleArrayObject(this.getFeature(path)));
        // Đầu tiên là trích xuất vector đặc trưng của ảnh đó
        Chunk[]  listImage = StorageEngine.read();
        // Đọc tất cả các Dữ liệu đã được lưu trong hệ thống
        
        List<String> listPath = new ArrayList<>();
        List<List<Double>>  listFeature = new ArrayList<>();
        for (int i = 0; i < listImage.length; i++) {
            Chunk tmp = listImage[i];
            listPath.add(tmp.path);
            listFeature.add(Arrays.asList(this.getDoubleArrayObject(tmp.data)));
        }

        List<Integer> sorted = VectorTool.getSortedIndex(target, listFeature);
        // thực hiện sắp xếp theo độ đo khoảng cách

        List<String> result = new ArrayList<>();
        
        for (int i = 0; i < 10; i ++) {
            result.add(listPath.get(sorted.get(i)));
        }
        return result.toArray(String[]::new);
    }


    public static void main(String[] args) throws Exception {


        String path = "G:\\image_db\\6bd21bc7018a51141662c151e2bdeeab.jpg";
        FindImageSimilarApp hogedge = new FindImageSimilarApp();
//        hogedge.addToDataSet(hogedge.imageFolder);

//        double[] arr = hogedge.getHogFeture(path);
//        for (int i = 0; i < arr.length; i++) {
//            System.out.printf("%d %2f \n",i, arr[i]);
//        }
        DisplayUtilities.display(ImageUtilities.readF(new File(path)));
        hogedge.getSimilar(path);
        MBFImage image = ImageUtilities.readMBF(new File(path));
        DisplayUtilities.display(image, "Ảnh Gốc");
    }
}
