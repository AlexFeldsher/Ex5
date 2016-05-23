package fileprocessing;

import java.io.*;
import java.util.*;


public class DirectoryProcessor {
    public static void main(String[] args) {
        // holds all the files in the source dir
        ArrayList<File> filesList = new ArrayList<>();

        String sourceDirPath = args[0];
        String commandFilePath = args[1];
        try {
            FileReader commadFileReader = new FileReader(commandFilePath);
        } catch (FileNotFoundException e) {
            // TODO: handle file not found
        }

        File sourceDir = new File(sourceDirPath);
        for (File f : sourceDir.listFiles(new WritableFileFilter())) {
            System.out.println(f.getName());
        }
    }
}
