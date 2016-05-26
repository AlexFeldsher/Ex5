package filesprocessing.comperators;

import filesprocessing.exceptions.TypeIError;

import java.io.File;
import java.util.Comparator;

/**
 * File comparator factory class
 */
public class FileComparatorFactory {
    /* constants representing the available comparators */
    private final String FILE_NAME = "abs";
    private final String FILE_TYPE = "type";
    private final String FILE_SIZE = "size";

    /**
     * Static method that returns a comparator
     *
     * @param order Type of comparator
     * @return a comparator
     * @throws TypeIError the requested comparator doesn't exist
     */
    public static Comparator<File> select(String order) throws TypeIError {
        if (order == null) {
            throw new TypeIError();
        }
        if (order.equals(FILE_NAME)) {
            return new FileNameComparator();
        }
        if (order.equals(FILE_TYPE)) {
            return new DoubleComparator(new FileTypeComparator(), new FileNameComparator());
        }
        if (order.equals(FILE_SIZE)) {
            return new DoubleComparator(new FileSizeComparator(), new FileNameComparator());
        }
        throw new TypeIError();
    }
}
