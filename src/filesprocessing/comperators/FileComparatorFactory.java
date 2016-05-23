package filesprocessing.comperators;

import filesprocessing.exceptions.TypeIError;

import java.io.File;
import java.util.Comparator;

public class FileComparatorFactory {
    public static Comparator<File> select(String order) throws TypeIError {
        if (order.equals("abs")) {
            return new FileNameComparator();
        }
        if (order.equals("type")) {
            return new FileTypeComparator();
        }
        if (order.equals("size")) {
            return new FileSizeComparator();
        }
        throw new TypeIError();
    }
}
