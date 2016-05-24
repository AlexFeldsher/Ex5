package filesprocessing.filefilters;

import java.io.File;
import java.io.FileFilter;

/**
 * Filters files smaller than a given value
 */
public class SmallerThanFileFilter implements FileFilter {
    private final double SMALLER_THAN_VALUE;
    private final int KILOBYTE = 1024;

    /**
     * Smaller than filter constructor
     * @param value the value that should be filtered against in KB
     */
    public SmallerThanFileFilter(double value) {
        SMALLER_THAN_VALUE = value * KILOBYTE;
    }

    public boolean accept(File file) {
        if (file.length() < SMALLER_THAN_VALUE) {
            return file.isFile();
        }
        return false;
    }
}
