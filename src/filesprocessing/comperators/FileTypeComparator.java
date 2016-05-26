package filesprocessing.comperators;

import java.io.File;
import java.util.Comparator;

/**
 * File type comparator, compares file types.
 */
public class FileTypeComparator implements Comparator<File> {
    /* Constant of the file type extension delimiter */
    private final String TYPE_DELIMITER = "\\.";

    public int compare(File file1, File file2) {
        // file type is determined by the string after the last . (dot) in the file name
        System.out.println("IN TYPECOMPARATOR");
        String[] fullFileName1 = file1.getName().split(TYPE_DELIMITER);
        String[] fullFileName2 = file2.getName().split(TYPE_DELIMITER);
        String fileType1;
        String fileType2;

        if (fullFileName1.length == 1) {
            fileType1 = fullFileName1[0];
        } else {
            fileType1 = fullFileName1[fullFileName1.length - 1];
        }

        if (fullFileName2.length == 1) {
            fileType2 = fullFileName2[0];
        } else {
            fileType2 = fullFileName2[fullFileName2.length - 1];
        }

        return fileType1.compareTo(fileType2);
    }
}
