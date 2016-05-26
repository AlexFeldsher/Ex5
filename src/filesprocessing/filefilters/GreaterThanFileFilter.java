package filesprocessing.filefilters;

import java.io.File;
import java.io.FileFilter;

/**
 * Filters files greater than a given file size value
 */
public class GreaterThanFileFilter implements FileFilter {
    /* holds the value to compare to */
    private final double GREATER_THAN_VALUE;

    /* Constant - the byte size of a kilobyte */
    private final int KILOBYTE = 1024;

    /**
     * Constructor for greater than file filter
     * @param value the value that should be filtered against in KB
     */
    public GreaterThanFileFilter(double value) {
        GREATER_THAN_VALUE = value * KILOBYTE;
    }

    /**
     * Tests whether or not the specified abstract pathname should be included in a pathname list.
     * Accepts files with file size strictly greater than the give size.
     *
     * @param file File object
     * @return true if the file size is greater than the give size, otherwise false.
     */
    public boolean accept(File file) {
        if (file.length() > GREATER_THAN_VALUE) {
            return file.isFile();
        }
        return false;
    }
}
