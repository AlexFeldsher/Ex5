package filesprocessing;

import filesprocessing.comperators.FileComparatorFactory;
import filesprocessing.comperators.FileNameComparator;
import filesprocessing.exceptions.BadCommandFileFormat;
import filesprocessing.exceptions.BadSubSectionNameException;
import filesprocessing.exceptions.TypeIError;
import filesprocessing.filefilters.AllFileFilter;
import filesprocessing.filefilters.FileFilterFactory;

import java.io.*;
import java.util.*;

public class DirectoryProcessor_OLD {

    public static void main(String[] args) {
        // holds all the files in the source dir
        ArrayList<File> filesList = new ArrayList<>();
        DirectoryProcessor_OLD dp = new DirectoryProcessor_OLD();

        String sourceDirPath = args[0];
        String commandFilePath = args[1];
        System.out.println("***********************");
        System.out.println(sourceDirPath + "\n" + commandFilePath);
        System.out.println("-----------------------");
        try {
            File sourceDir = new File(sourceDirPath);
            FileReader commandFileReader = new FileReader(commandFilePath);
            LineNumberReader commandLineReader = new LineNumberReader(commandFileReader);
            String line = null;
            FileFilter fileFilter = null;
            Comparator<File> fileComparator;
            while ((line = commandLineReader.readLine()) != null) {
                // Create filtered file list
                if (line.equals("FILTER")) {
                    line = commandLineReader.readLine();
                    String[] splitLine = line.split("#");
                    Boolean not = splitLine[splitLine.length - 1].equals("NOT") ? true : false;
                    try {
                        fileFilter = FileFilterFactory.select(line);
                    } catch (TypeIError e) {
                        System.err.println("Warning in line " + commandLineReader.getLineNumber());
                        fileFilter = new AllFileFilter();
                    } catch (BadCommandFileFormat e) {
                        return;
                    }
                    for (File f : sourceDir.listFiles(fileFilter)) {
                        filesList.add(f);
                    }
                    if (not) {
                        ArrayList<File> notFileList = new ArrayList<>();
                        for (File f : sourceDir.listFiles()) {
                            if (!filesList.contains(f)) {
                                notFileList.add(f);
                            }
                        }
                        filesList = notFileList;
                    }
                    line = commandLineReader.readLine();
                    if (line == null) {
                        throw new BadSubSectionNameException();
                    }
                    if (line.equals("ORDER")) {
                        line = commandLineReader.readLine();
                        if (line == null) {
                            System.err.println("Warning in line " + commandLineReader.getLineNumber());
                            continue;
                        }
                        splitLine = line.split("#");
                        Boolean reverse = splitLine[splitLine.length - 1].equals("NOT") ? true : false;
                        try {
                            fileComparator = FileComparatorFactory.select(splitLine[0]);
                        } catch (TypeIError e) {
                            System.err.println("Warning in line " + commandLineReader.getLineNumber());
                            fileComparator = new FileNameComparator();
                        }
                        if (reverse) {
                            filesList.sort(fileComparator.reversed());
                        } else {
                            filesList.sort(fileComparator);
                        }
                    } else {
                        throw new BadSubSectionNameException();
                    }
                    for (File f : filesList) {
                        System.out.println(f.getName());
                        filesList = new ArrayList<>();
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
    }
}