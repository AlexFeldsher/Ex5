package fileprocessing.Tests;

import fileprocessing.filefilters.AllFileFilter;
import fileprocessing.FileFilterFactory;
import fileprocessing.exceptions.TypeIError;

import java.io.*;
import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        // holds all the files in the source dir
        ArrayList<File> filesList = new ArrayList<>();

        String sourceDirPath = args[0];
        FileReader commandFileReader;
        File commandFilePath = new File(args[1]);
        File sourceDir = new File(sourceDirPath);
        for (File f : commandFilePath.listFiles()) {
            try {
                commandFileReader = new FileReader(f);
            } catch (FileNotFoundException e) {
                // TODO: handle file not found
                return;
            }

            LineNumberReader commandFileLineReader = new LineNumberReader(commandFileReader);
            String line;
            FileFilter fileFilter = new AllFileFilter();
            try {
                String prevLine = "";
                while ((line = commandFileLineReader.readLine()) != null) {
                    if (prevLine.equals("FILTER") && !line.equals("FILTER") && !line.equals("ORDER")) {
                        String[] lineArr = line.split("#");
                        try {
                            fileFilter = FileFilterFactory.select(line);
                        } catch (TypeIError e) {
                            System.err.println(e);
                        }
                        // all files list
                        File[] allFilesArr = sourceDir.listFiles();
                        // filtered list
                        ArrayList<File> filteredFiles = new ArrayList<>();
                        for (File fl : sourceDir.listFiles(fileFilter)) {
                            System.out.println(line);
                            System.out.println(fl.getName());
                            filteredFiles.add(fl);
                        }
                        // not file list
                        ArrayList<File> notFiles = new ArrayList<>();
                        for (File fl : allFilesArr) {
                            for (File fx : allFilesArr) {
                                if (!filteredFiles.contains(fx)) {
                                    notFiles.add(fx);
                                }
                            }

                        }
                    }

                    prevLine = line;
                }
            } catch (IOException e) {
                System.err.println(e);
            }

        }

        /*
        File sourceDir = new File(sourceDirPath);
        for (File f : sourceDir.listFiles(new WritableFileFilter())) {
            System.out.println(f.getName());
        }
        */
    }
}
