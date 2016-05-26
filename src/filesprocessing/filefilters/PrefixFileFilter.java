package filesprocessing.filefilters;

import java.io.File;
import java.io.FileFilter;

/**
 * Filters files that their name start with a given prefix
 */
public class PrefixFileFilter implements FileFilter {
    /* holds the prefix to filter with */
    private final String PREFIX;

    /**
     * Prefix file filter constructor
     *
     * @param prefix The prefix to test against
     */
    public PrefixFileFilter(String prefix) {
        PREFIX = prefix;
    }

    /**
     * Tests whether or not the specified abstract pathname should be included in a pathname list.
     * Accepts files with a file name that contains the prefix the filter was initialized with.
     *
     * @param file File object
     * @return true if the file name begins with a given string, false otherwise
     */
    public boolean accept(File file) {
        if (file.getName().startsWith(PREFIX)) {
            return file.isFile();
        }
        return false;
    }
}
