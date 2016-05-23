package filesprocessing.comperators;

import java.io.File;
import java.util.Comparator;

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
        return 0;
    }
}
