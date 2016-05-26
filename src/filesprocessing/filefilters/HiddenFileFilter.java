package filesprocessing.filefilters;

import filesprocessing.exceptions.BadFilterState;

import java.io.File;
import java.io.FileFilter;

/**
 * Filter files that are hidden/not-hidden, depending on the state the filter initialized with
 */
public class HiddenFileFilter implements FileFilter {

    /* holds the state of the filter. "YES" if writable, "NO" if not writable */
    private final String filterState;

    /* Constants of the filter state */
    private final String HIDDEN = "YES";
    private final String NOT_HIDDEN = "NO";

    /**
     * Hidden filter constructor
     *
     * @param state YES if hidden, NO not hidden
     * @throws BadFilterState if given state isn't supported
     */
    public HiddenFileFilter(String state) throws BadFilterState {
        filterState = state;
        if (!filterState.equals(HIDDEN) && !filterState.equals(NOT_HIDDEN)) {
            throw new BadFilterState();
        }
    }

    /**
     * Tests whether or not the specified abstract pathname should be included in a pathname list.
     * Accepts hidden files or not hidden files depending on the state the filter was initialized with.
     *
     * @param file File object
     * @return true if file accepted, false otherwise
     */
    public boolean accept(File file) {
        if (filterState.equals(HIDDEN)) {
            return (file.isHidden() && file.isFile());
        } else {
            return (!file.isHidden() && file.isFile());
        }
    }
}
