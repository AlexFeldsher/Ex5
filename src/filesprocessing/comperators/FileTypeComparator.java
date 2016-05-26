package filesprocessing.comperators;

import java.io.File;
import java.util.Comparator;

public class FileTypeComparator implements Comparator<File> {
    /* Constant of the file type extension delimiter */
    private final String TYPE_DELIMITER = "\\.";

    public int compare(File file1, File file2) {
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

        if (fileType1.compareTo(fileType2) == 0) {
            return file1.getName().compareTo((file2.getName()));
        } else {
            return fileType1.compareTo(fileType2);
        }
    }
}
