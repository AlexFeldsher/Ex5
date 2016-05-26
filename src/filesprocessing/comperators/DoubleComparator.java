package filesprocessing.comperators;

import java.io.File;
import java.util.Comparator;

/**
 * 2 level comparator. Compares with the first comparator, if the files are the equal then it compares with
 * the second comparator.
 */
public class DoubleComparator implements Comparator<File> {
    /* Constants holding the comparators */
    private final Comparator<File> FIRST_COMPARATOR;
    private final Comparator<File> SECOND_COMPARATOR;

    /**
     * Constructor for DoubleComparator
     *
     * @param comparator1 The first comparator to be used
     * @param comparator2 The second comparator to be used
     */
    public DoubleComparator(Comparator<File> comparator1, Comparator<File> comparator2) {
        FIRST_COMPARATOR = comparator1;
        SECOND_COMPARATOR = comparator2;
    }

    public int compare(File file1, File file2) {
        // compare the files with the first comparator
        int result = FIRST_COMPARATOR.compare(file1, file2);

        // if the files are equal then compare them with the second comparator
        if (result == 0) {
            return SECOND_COMPARATOR.compare(file1, file2);
        }

        return result;
    }
}
