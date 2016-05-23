package fileprocessing;

import java.io.*;
import java.util.*;

public class DirectoryProcessor {

    public static void main(String[] args) {
        // holds all the files in the source dir
        ArrayList<File> filesList = new ArrayList<>();
        DirectoryProcessor dp = new DirectoryProcessor();

        String sourceDirPath = args[0];
        String commandFilePath = args[1];
        try {
            File sourceDir = new File(sourceDirPath);
            FileReader commandFileReader = new FileReader(commandFilePath);
            LineNumberReader commandLineReader = new LineNumberReader(commandFileReader);
            String line = null;
            FileFilter fileFilter = null;
            while ((line = commandLineReader.readLine()) != null) {
                // Create filtered file list
                if (line.equals("FILTER")) {
                    line = commandLineReader.readLine();
                    try {
                        fileFilter = FileFilterFactory.select(line);
                    } catch (TypeIError e) {
                        System.err.println("Warning in line " + commandLineReader.getLineNumber());
                        fileFilter = new AllFileFilter();
                    }
                    for (File f : sourceDir.listFiles(fileFilter)) {
                        filesList.add(f);
                    }
                    line = commandLineReader.readLine();
                    if (line.equals("ORDER")) {
                        line = commandLineReader.readLine();
                    } else {
                        throw new BadSubSectionNameException();
                    }
                } else {
                    System.err.println("Warning in line " + commandLineReader.getLineNumber());
                }
            }
        } catch (FileNotFoundException e) {
            // TODO: handle file not found
        } catch (IOException e) {
            // TODO: handle exception
        } catch (BadSubSectionNameException e) {
            // TODO: handle exception
        }


        File sourceDir = new File(sourceDirPath);
        for (File f : sourceDir.listFiles(new WritableFileFilter())) {
            System.out.println(f.getName());
        }
    }
}
