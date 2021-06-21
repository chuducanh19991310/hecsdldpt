package com.btl.hecsdlpt.imageSearch.storage;

import javassist.bytecode.stackmap.BasicBlock;
import org.openimaj.io.Cache;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class StorageEngine {

    public static String path = "src/main/data";

    static public  void write(Chunk[] chunks) {
        try {
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(chunks);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    static public Chunk[] read() {
        try {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois  = new ObjectInputStream(fis);
            return (Chunk[]) ois.readObject();
         } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }



}
