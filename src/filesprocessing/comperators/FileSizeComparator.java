package filesprocessing.comperators;

import java.io.File;
import java.util.Comparator;

/**
 * File size comparator, compares files according to file size.
 * If file sizes are the same, then they are returned in a lexicographical order.
 */
public class FileSizeComparator implements Comparator<File> {
    public int compare(File file1, File file2) {
        long fileSize1 = file1.length();
        long fileSize2 = file2.length();
        if (fileSize1 < fileSize2) {
            return -1;
        }
        if (fileSize1 > fileSize2) {
            return 1;
        }
        // files equal in size
        String fileName1 = file1.getName();
        String fileName2 = file2.getName();
        return fileName1.compareTo(fileName2);
    }
}
