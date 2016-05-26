package filesprocessing.comperators;

import java.io.File;
import java.util.Comparator;

public class FileNameComparator implements Comparator<File> {
    public int compare(File file1, File file2) {
        System.out.println("IN FILENAME COMPARATOR");
        String fileName1 = file1.getName();
        String fileName2 = file2.getName();
        return fileName1.compareTo(fileName2);
    }
}
