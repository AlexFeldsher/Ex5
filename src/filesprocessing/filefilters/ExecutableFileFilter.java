package filesprocessing.filefilters;

import filesprocessing.exceptions.BadFilterParameterException;
import filesprocessing.exceptions.BadFilterState;

import java.io.File;
import java.io.FileFilter;

/**
 * Filter files that can be executed, or non-executable,
 * depending on the state the filter is initialized with
 */
public class ExecutableFileFilter implements FileFilter {

    /* holds the state of the filter. "YES" if writable, "NO" if not writable */
    private final String filterState;

    /* Constants of the filter state */
    private final String EXECUTABLE = "YES";
    private final String NOT_EXECUTABLE = "NO";

    /**
     * Executable filter constructor
     *
     * @param state YES if executable, NO if not executable
     * @throws BadFilterParameterException if given state isn't supported
     */
    public ExecutableFileFilter(String state) throws BadFilterParameterException {
        filterState = state;
        if (!filterState.equals(EXECUTABLE) && !filterState.equals(NOT_EXECUTABLE)) {
            throw new BadFilterParameterException();
        }
    }

    /**
     * Tests whether or not the specified abstract pathname should be included in a pathname list.
     * Accepts executable files or non-executable file depending on the state the filter was
     * initialized with.
     *
     * @param file File object
     * @return true file was accepted, false otherwise
     */
    public boolean accept(File file) {
        if (filterState.equals(EXECUTABLE)) {
            return (file.canExecute() && file.isFile());
        } else {
            return (!file.canExecute() && file.isFile());
        }
    }
}
