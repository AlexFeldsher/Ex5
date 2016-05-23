package filesprocessing.filefilters;

import java.io.File;
import java.io.FileFilter;

/**
 * Filters all the given files
 */
public class AllFileFilter implements FileFilter {
    public boolean accept(File file) {
        return true;
    }
}
