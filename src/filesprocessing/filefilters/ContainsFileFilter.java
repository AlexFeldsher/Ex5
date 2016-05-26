package filesprocessing.filefilters;

import java.io.File;
import java.io.FileFilter;

/**
 * Filters files that contain a string in the name
 */
public class ContainsFileFilter implements FileFilter {

    /* the string to filter against */
    private final String CONTAINS_STRING;

    /**
     * Contains filter constructor
     *
     * @param containsString filter files that contain this string
     */
    public ContainsFileFilter(String containsString) {
        CONTAINS_STRING = containsString;
    }

    /**
     * Tests whether or not the specified abstract pathname should be included in a pathname list.
     * Accepts files that their file name contains a given string.
     *
     * @param file File object
     * @return true if the file name contains the given string, false otherwise
     */
    public boolean accept(File file) {
        if (file.getName().contains(CONTAINS_STRING)) {
            return file.isFile();
        }
        return false;
    }
}
