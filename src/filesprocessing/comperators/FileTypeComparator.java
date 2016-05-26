package filesprocessing.comperators;

import java.io.File;
import java.util.Comparator;

/**
 * File type comparator, compares file types.
 * If file types are the same, then they are returned in a lexicographical order.
 */
public class FileTypeComparator implements Comparator<File> {
    /* Constant of the file type extension delimiter */
    private final String TYPE_DELIMITER = "\\.";

    /**
     * Compare file type (lexicographically). Returns a negative integer, zero, or a positive integer as
     * the first argument is less than, equal to, or greater than the second.
     *
     * @param file1 a file object
     * @param file2 a file object
     * @return a negative integer, zero, or a positive integer as the first argument is less than, equal to,
     * or greater than the second
     */
    public int compare(File file1, File file2) {
        // get file type strings
        String[] fileTypes = getFileTypes(file1, file2);
        String fileType1 = fileTypes[0];
        String fileType2 = fileTypes[1];

        int result = fileType1.compareTo(fileType2);

        // if both file types are the same sort by file name
        if (result == 0) {
            String fileName1 = file1.getName();
            String fileName2 = file2.getName();
            return fileName1.compareTo(fileName2);
        }

        return result;
    }

    /* Returns a string array containing 2 file types, arr[0] = type of file1, arr[1] = type of file 2 */
    private String[] getFileTypes(File file1, File file2) {
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

        String[] fileTypeArray = {fileType1, fileType2};
        return fileTypeArray;
    }
}
