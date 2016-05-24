package filesprocessing.filefilters;

import filesprocessing.exceptions.BadFilterState;

import java.io.File;
import java.io.FileFilter;

/**
 * Filter files that are hidden
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
     * @param state YES if writable, NO otherwise
     * @throws BadFilterState if given state isn't supported
     */
    public HiddenFileFilter(String state) throws BadFilterState {
        filterState = state;
        if (!filterState.equals(HIDDEN) && !filterState.equals(NOT_HIDDEN)) {
            throw new BadFilterState();
        }
    }

    public boolean accept(File file) {
        if (filterState.equals(HIDDEN)) {
            return file.isHidden();
        } else {
            return !file.isHidden();
        }
    }
}
