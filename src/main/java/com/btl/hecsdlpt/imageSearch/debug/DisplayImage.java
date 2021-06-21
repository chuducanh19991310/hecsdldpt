package com.btl.hecsdlpt.imageSearch.debug;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class DisplayImage {

    public static void main(String[] args) throws Exception {
        showImage("F:/coding/project/hecsdldpt/public/prepare/image/0b4de4a971bfbe553fe3307a7d2acba4.jpg");
    }

    public static void showImage(String path) throws Exception {
        BufferedImage img= ImageIO.read(new File(path));
        ImageIcon icon=new ImageIcon(img);
        JFrame frame=new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(200,300);
        JLabel lbl=new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
