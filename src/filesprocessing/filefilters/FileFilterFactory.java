package filesprocessing.filefilters;

import filesprocessing.exceptions.BadCommandFileFormat;
import filesprocessing.exceptions.BadFilterState;
import filesprocessing.exceptions.TypeIError;

import java.io.FileFilter;

public class FileFilterFactory {
    private static final String COMMAND_VALUE_DELIMITER = "#";

    public static FileFilter select(String commandString) throws TypeIError, BadCommandFileFormat {
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
            if (commandArray[1] == null) {
                throw new TypeIError();
            }
            if (commandArray.length > 2 && commandArray[2] != null) {
                if (!commandArray[2].equals("NOT")) {
                    throw new TypeIError();
                }
            }
            try {
                return new WritableFileFilter(commandArray[1]);
            } catch (BadFilterState e) {
                throw new TypeIError();
            }
        } else if (filter.equals("executable")) {
            if (commandArray[1] == null) {
                throw new TypeIError();
            }
            if (commandArray.length > 2 && commandArray[2] != null) {
                if (!commandArray[2].equals("NOT")) {
                    throw new TypeIError();
                }
            }
            try {
                return new ExecutableFileFilter(commandArray[1]);
            } catch (BadFilterState e) {
                throw new TypeIError();
            }
        } else if (filter.equals("hidden")) {
            if (commandArray[1] == null) {
                throw new TypeIError();
            }
            if (commandArray.length > 2 && commandArray[2] != null) {
                if (!commandArray[2].equals("NOT")) {
                    throw new TypeIError();
                }
            }
            try {
                return new HiddenFileFilter(commandArray[1]);
            } catch (BadFilterState e) {
                throw new TypeIError();
            }
        } else if (filter.equals("all")) {
            return new AllFileFilter();
        }
        throw new BadCommandFileFormat();
    }
}
