package fileprocessing;

import java.io.File;
import java.io.FileFilter;

/**
 * Filters files greater than a given value
 */
public class GreaterThanFileFilter implements FileFilter {
    /* holds the value to compare to */
    private double greaterThanValue;

    /**
     * Constructor for
     *
     * @param value the value that should be filtered against
     */
    public GreaterThanFileFilter(double value) {
        greaterThanValue = value;
    }

    public boolean accept(File pathname) {
        double fileSizeKB = pathname.length() / 1024;
        if (fileSizeKB > greaterThanValue) {
            return true;
        }
        return false;
    }
}
