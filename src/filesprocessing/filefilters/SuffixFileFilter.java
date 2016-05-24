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

    public boolean accept(File file) {
        if (file.getName().endsWith(SUFFIX)) {
            return file.isFile();
        }
        return false;
    }
}
