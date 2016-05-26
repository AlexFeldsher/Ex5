package filesprocessing.filefilters;

import java.io.File;
import java.io.FileFilter;

/**
 * Filters all the given files
 */
public class AllFileFilter implements FileFilter {
    /**
     * Tests whether or not the specified abstract pathname should be included in a pathname list.
     * Accepts all files.
     *
     * @param file File object
     * @return true if accepted, false otherwise
     */
    public boolean accept(File file) {
        return file.isFile();
    }
}
