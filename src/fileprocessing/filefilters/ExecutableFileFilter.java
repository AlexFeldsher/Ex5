package fileprocessing.filefilters;

import java.io.File;
import java.io.FileFilter;

/**
 * Filter files that can be executed
 */
public class ExecutableFileFilter implements FileFilter {
    public boolean accept(File file) {
        return file.canExecute();
    }
}
