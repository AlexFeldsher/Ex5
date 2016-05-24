package filesprocessing.filefilters;

import filesprocessing.exceptions.BadFilterState;

import java.io.File;
import java.io.FileFilter;

/**
 * Writable file filter
 */
public class WritableFileFilter implements FileFilter {

    /* holds the state of the filter. "YES" if writable, "NO" if not writable */
    private final String filterState;

    /* Constants of the filter state */
    private final String WRITABLE = "YES";
    private final String NOT_WRITABLE = "NO";

    /**
     * Writable filter constructor
     *
     * @param state YES if writable, NO otherwise
     * @throws BadFilterState if given state isn't supported
     */
    public WritableFileFilter(String state) throws BadFilterState {
        filterState = state;
        if (!filterState.equals(WRITABLE) && !filterState.equals(NOT_WRITABLE)) {
            throw new BadFilterState();
        }
    }

    public boolean accept(File file) {
        if (filterState.equals(WRITABLE)) {
            return file.canWrite();
        } else {
            return !file.canWrite();
        }
    }
}
