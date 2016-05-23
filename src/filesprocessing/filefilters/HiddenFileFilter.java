package filesprocessing.filefilters;

import java.io.File;
import java.io.FileFilter;

/**
 * Filter files that are hidden
 */
public class HiddenFileFilter implements FileFilter {
    public boolean accept(File file) {
        return file.isHidden();
    }
}
