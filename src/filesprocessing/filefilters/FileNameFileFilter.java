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

    /**
     * Tests whether or not the specified abstract pathname should be included in a pathname list.
     * Accepts files that their file name is a given string.
     *
     * @param file File object
     * @return true if the file name is the given string, false otherwise
     */
    public boolean accept(File file) {
        if (file.getName().equals(FILE_NAME)) {
            return file.isFile();
        }
        return false;
    }
}
