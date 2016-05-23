package fileprocessing;

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

    public boolean accept(File file) {
        if (file.getName().startsWith(PREFIX)) {
            return true;
        }
        return false;
    }
}
