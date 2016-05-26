package filesprocessing.comperators;

import java.io.File;
import java.util.Comparator;

/**
 * File name comparator, sort files by file name lexicographically
 */
public class FileNameComparator implements Comparator<File> {
    /**
     * Compare file names. Returns a negative integer, zero, or a positive integer as the first argument is
     * less than, equal to, or greater than the second (lexicographically).
     *
     * @param file1 a file object
     * @param file2 a file object
     * @return a negative integer, zero, or a positive integer as the first argument is less than, equal to,
     * or greater than the second
     */
    public int compare(File file1, File file2) {
        String fileName1 = file1.getName();
        String fileName2 = file2.getName();
        return fileName1.compareTo(fileName2);
    }
}
