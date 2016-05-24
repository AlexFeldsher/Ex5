package filesprocessing.filefilters;

import java.io.File;
import java.io.FileFilter;

/**
 * Filters files two given file size value
 */
public class BetweenFileFilter implements FileFilter {

    /* Hold the lower and upper limit values */
    private final double LOWER_LIMIT;
    private final double UPPER_LIMIT;

    private final int KILOBYTE = 1024;

    /**
     * Constructor for between file filter
     *
     * @param x lower limit file size in KB
     * @param y upper limit file size in KB
     */
    public BetweenFileFilter(double x, double y) {
        LOWER_LIMIT = x * KILOBYTE;
        UPPER_LIMIT = y * KILOBYTE;
    }

    public boolean accept(File file) {
        if (file.length() >= LOWER_LIMIT && file.length() <= UPPER_LIMIT) {
            return file.isFile();
        }
        return false;
    }
}
