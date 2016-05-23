package fileprocessing.comperators;

import java.io.File;
import java.util.Comparator;

public class FileTypeComparator implements Comparator<File> {
    /* Constant of the file type extension delimiter */
    private final String TYPE_DELIMITER = ".";

    public int compare(File file1, File file2) {
        String[] fullFileName1 = file1.getName().split(TYPE_DELIMITER);
        String[] fullFileName2 = file2.getName().split(TYPE_DELIMITER);
        String fileType1 = fullFileName1[fullFileName1.length - 1];
        String fileType2 = fullFileName2[fullFileName2.length - 1];
        return fileType1.compareTo(fileType2);
    }
}
