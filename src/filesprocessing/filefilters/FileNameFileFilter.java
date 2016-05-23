package filesprocessing.filefilters;

import java.io.File;
import java.io.FileFilter;

/**
 * Filters files according to file name
 */
public class FileNameFileFilter implements FileFilter {
    private final String FILE_NAME;

    /**
     * File name filter constructor
     *
     * @param fileName the file name to filter against
     */
    public FileNameFileFilter(String fileName) {
        FILE_NAME = fileName;
    }

    public boolean accept(File file) {
        if (file.getName().equals(FILE_NAME)) {
            return true;
        }
        return false;
    }
}
