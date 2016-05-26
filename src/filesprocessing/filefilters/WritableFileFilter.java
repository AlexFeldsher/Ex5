package filesprocessing.filefilters;

import filesprocessing.exceptions.BadFilterParameterException;

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
     * @throws BadFilterParameterException if given state isn't supported
     */
    public WritableFileFilter(String state) throws BadFilterParameterException {
        filterState = state;
        if (!filterState.equals(WRITABLE) && !filterState.equals(NOT_WRITABLE)) {
            throw new BadFilterParameterException();
        }
    }

    /**
     * Tests whether or not the specified abstract pathname should be included in a pathname list.
     * Accepts files that are writable/non-writable depending on the state the filter was initialized with.
     *
     * @param file File object
     * @return true if accepted, otherwise false.
     */
    public boolean accept(File file) {
        if (filterState.equals(WRITABLE)) {
            return (file.canWrite() && file.isFile());
        } else {
            return (!file.canWrite() && file.isFile());
        }
    }
}
