package filesprocessing.filefilters;

import filesprocessing.exceptions.BadFilterState;

import java.io.File;
import java.io.FileFilter;

/**
 * Filter files that can be executed
 */
public class ExecutableFileFilter implements FileFilter {

    /* holds the state of the filter. "YES" if writable, "NO" if not writable */
    private final String filterState;

    /* Constants of the filter state */
    private final String WRITABLE = "YES";
    private final String NOT_WRITABLE = "NO";

    /**
     * Executable filter constructor
     *
     * @param state YES if writable, NO otherwise
     * @throws BadFilterState if given state isn't supported
     */
    public ExecutableFileFilter(String state) throws BadFilterState {
        filterState = state;
        if (!filterState.equals(WRITABLE) && !filterState.equals(NOT_WRITABLE)) {
            throw new BadFilterState();
        }
    }

    public boolean accept(File file) {
        if (filterState.equals(WRITABLE)) {
            return (file.canExecute() && file.isFile());
        } else {
            return (!file.canExecute() && file.isFile());
        }
    }
}
