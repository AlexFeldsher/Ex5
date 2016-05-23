package fileprocessing;

import java.io.File;
import java.io.FileFilter;

/**
 * Filters files that contain a string in the name
 */
public class ContainsFileFilter implements FileFilter {
    private final String CONTAINS_STRING;

    /**
     * Contains filter constructor
     *
     * @param containsString filter files that contain this string
     */
    public ContainsFileFilter(String containsString) {
        CONTAINS_STRING = containsString;
    }

    public boolean accept(File file) {
        if (file.getName().contains(CONTAINS_STRING)) {
            return true;
        }
        return false;
    }
}
