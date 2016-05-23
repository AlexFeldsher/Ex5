package filesprocessing.filefilters;

import java.io.File;
import java.io.FileFilter;

/**
 * Filters files greater than a given file size value
 */
public class GreaterThanFileFilter implements FileFilter {
    /* holds the value to compare to */
    private final double GREATER_THAN_VALUE;

    /**
     * Constructor for greater than file filter
     * @param value the value that should be filtered against
     */
    public GreaterThanFileFilter(double value) {
        GREATER_THAN_VALUE = value;
    }

    public boolean accept(File file) {
        double fileSizeKB = file.length() / 1024;
        if (fileSizeKB > GREATER_THAN_VALUE) {
            return true;
        }
        return false;
    }
}
