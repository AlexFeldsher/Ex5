package filesprocessing.filefilters;

import java.io.File;
import java.io.FileFilter;

/**
 * Filters files greater than a given file size value
 */
public class GreaterThanFileFilter implements FileFilter {
    /* holds the value to compare to */
    private final double GREATER_THAN_VALUE;

    private final int KILOBYTE = 1024;

    /**
     * Constructor for greater than file filter
     * @param value the value that should be filtered against in KB
     */
    public GreaterThanFileFilter(double value) {
        GREATER_THAN_VALUE = value * KILOBYTE;
    }

    public boolean accept(File file) {
        if (file.length() > GREATER_THAN_VALUE) {
            return true;
        }
        return false;
    }
}
