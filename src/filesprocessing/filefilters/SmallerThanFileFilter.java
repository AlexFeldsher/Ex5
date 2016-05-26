package filesprocessing.filefilters;

import java.io.File;
import java.io.FileFilter;

/**
 * Filters files smaller than a given value.
 */
public class SmallerThanFileFilter implements FileFilter {
    /* Holds that value to filter with */
    private final double SMALLER_THAN_VALUE;

    /* Constant - the byte size of a kilobyte */
    private final int KILOBYTE = 1024;

    /**
     * Smaller than filter constructor
     * @param value the value that should be filtered against in KB
     */
    public SmallerThanFileFilter(double value) {
        SMALLER_THAN_VALUE = value * KILOBYTE;
    }

    /**
     * Tests whether or not the specified abstract pathname should be included in a pathname list.
     * Accepts files with file sizes strictly smaller than the value the filter was initialized with.
     *
     * @param file File object
     * @return true if file size is smaller that the value the filter was initialized with, otherwise false
     */
    public boolean accept(File file) {
        if (file.length() < SMALLER_THAN_VALUE) {
            return file.isFile();
        }
        return false;
    }
}
