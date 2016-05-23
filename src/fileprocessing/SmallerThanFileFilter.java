package fileprocessing;

import java.io.File;
import java.io.FileFilter;

/**
 * Filters files smaller than a given value
 */
public class SmallerThanFileFilter implements FileFilter {
    private final double SMALLER_THAN_VALUE;

    /**
     * Smaller than filter constructor
     *
     * @param value the value that should be filtered against
     */
    public SmallerThanFileFilter(double value) {
        SMALLER_THAN_VALUE = value;
    }

    public boolean accept(File file) {
        if (file.length() < SMALLER_THAN_VALUE) {
            return true;
        }
        return false;
    }
}
