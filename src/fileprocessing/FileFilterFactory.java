package fileprocessing;

import fileprocessing.exceptions.TypeIError;
import fileprocessing.filefilters.*;

import java.io.FileFilter;

public class FileFilterFactory {
    private static final String COMMAND_VALUE_DELIMITER = "#";

    public static FileFilter select(String commandString) throws TypeIError {
        String[] commandArray = commandString.split(COMMAND_VALUE_DELIMITER);
        String filter = commandArray[0];
        if (filter.equals("greater_than")) {
            double value;
            try {
                value = new Double(commandArray[1]);
            } catch (NumberFormatException e) {
                throw new TypeIError();
            }
            if (value < 0) {
                throw new TypeIError();
            }
            return new GreaterThanFileFilter(value);
        } else if (filter.equals("between")) {
            double lowerLimit;
            double upperLimit;
            try {
                lowerLimit = new Double(commandArray[1]);
                upperLimit = new Double(commandArray[2]);
            } catch (NumberFormatException e) {
                throw new TypeIError();
            }
            if (upperLimit < lowerLimit) {
                throw new TypeIError();
            }
            return new BetweenFileFilter(lowerLimit, upperLimit);
        } else if (filter.equals("smaller_than")) {
            double value;
            try {
                value = new Double(commandArray[1]);
            } catch (NumberFormatException e) {
                throw new TypeIError();
            }
            if (value < 0) {
                throw new TypeIError();
            }
            return new SmallerThanFileFilter(value);
        } else if (filter.equals("file")) {
            String fileName;
            try {
                fileName = commandArray[1];
            } catch (IndexOutOfBoundsException e) {
                throw new TypeIError();
            }
            return new FileNameFileFilter(fileName);
        } else if (filter.equals("contains")) {
            String containsString;
            try {
                containsString = commandArray[1];
            } catch (IndexOutOfBoundsException e) {
                throw new TypeIError();
            }
            return new ContainsFileFilter(containsString);
        } else if (filter.equals("prefix")) {
            String prefix;
            try {
                prefix = commandArray[1];
            } catch (IndexOutOfBoundsException e) {
                throw new TypeIError();
            }
            return new PrefixFileFilter(prefix);
        } else if (filter.equals("suffix")) {
            String suffix;
            try {
                suffix = commandArray[1];
            } catch (IndexOutOfBoundsException e) {
                throw new TypeIError();
            }
            return new SuffixFileFilter(suffix);
        } else if (filter.equals("writable")) {
            return new WritableFileFilter();
        } else if (filter.equals("executable")) {
            return new ExecutableFileFilter();
        } else if (filter.equals("hidden")) {
            return new HiddenFileFilter();
        } else if (filter.equals("all")) {
            return new AllFileFilter();
        }
        return null;
    }
}
