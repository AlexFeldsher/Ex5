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

    /**
     * Constructor for between file filter
     *
     * @param x lower limit file size
     * @param y upper limit file size
     */
    public BetweenFileFilter(double x, double y) {
        LOWER_LIMIT = x;
        UPPER_LIMIT = y;
    }

    public boolean accept(File file) {
        double fileSizeKB = file.length() / 1024;
        if (fileSizeKB >= LOWER_LIMIT && fileSizeKB <= UPPER_LIMIT) {
            return true;
        }
        return false;
    }
}
