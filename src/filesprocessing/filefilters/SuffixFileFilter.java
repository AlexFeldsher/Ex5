package filesprocessing.filefilters;

import java.io.File;
import java.io.FileFilter;

/**
 * Filters files that their name end with a given suffix
 */
public class SuffixFileFilter implements FileFilter {
    /* holds the suffix to filter with */
    private final String SUFFIX;

    /**
     * Suffix file filter constructor
     *
     * @param suffix the suffix to filter against
     */
    public SuffixFileFilter(String suffix) {
        SUFFIX = suffix;
    }

    /**
     * Tests whether or not the specified abstract pathname should be included in a pathname list.
     * Accepts files that their file names end with the suffix the filter was initialized with.
     *
     * @param file File object
     * @return true if file name ends with a suffix, false otherwise.
     */
    public boolean accept(File file) {
        if (file.getName().endsWith(SUFFIX)) {
            return file.isFile();
        }
        return false;
    }
}
