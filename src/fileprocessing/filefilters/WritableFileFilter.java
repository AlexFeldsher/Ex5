package fileprocessing.filefilters;

import java.io.File;
import java.io.FileFilter;

/**
 * Writable file filter
 */
public class WritableFileFilter implements FileFilter {
    public boolean accept(File file) {
        return file.canWrite();
    }
}
