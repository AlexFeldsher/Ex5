package filesprocessing.filefilters;

import filesprocessing.exceptions.BadCommandFileFormat;
import filesprocessing.exceptions.BadFilterParameterException;
import filesprocessing.exceptions.BadFilterState;
import filesprocessing.exceptions.TypeIError;

import java.io.FileFilter;

/**
 * File filter factory class
 */
public class FileFilterFactory {
    /* constant of the filter command delimiter */
    private static final String COMMAND_VALUE_DELIMITER = "#";
    /* filter types constants */
    private static final String GREATER_THAN = "greater_than";
    private static final String BETWEEN = "between";
    private static final String SMALLER_THAN = "smaller_than";
    private static final String FILE = "file";
    private static final String CONTAINS = "contains";
    private static final String PREFIX = "prefix";
    private static final String SUFFIX = "suffix";
    private static final String WRITABLE = "writable";
    private static final String EXECUTABLE = "executable";
    private static final String HIDDEN = "hidden";
    private static final String ALL = "all";
    // TODO: remove it once fixed all other todos here
    /* filter state  constant*/
    private static final String NOT = "NOT";

    /**
     * Static method that returns a file filter
     *
     * @param commandString a filter string "type#state/NO/YES#NOT"
     * @return file filter
     * @throws BadFilterParameterException bad commandString parameters
     */
    public static FileFilter select(String commandString) throws BadFilterParameterException,
            NullPointerException {
        String[] commandArray = commandString.split(COMMAND_VALUE_DELIMITER);
        String filter = commandArray[0];
        if (filter.equals(GREATER_THAN)) {   // greater than filter generator
            double value;
            try {
                value = new Double(commandArray[1]);
            } catch (NumberFormatException e) {
                throw new BadFilterParameterException();
            }
            if (value < 0) {
                throw new BadFilterParameterException();
            }
            return new GreaterThanFileFilter(value);
        } else if (filter.equals(BETWEEN)) {  // between than filter generator
            double lowerLimit;
            double upperLimit;
            try {
                lowerLimit = new Double(commandArray[1]);
                upperLimit = new Double(commandArray[2]);
            } catch (NumberFormatException e) {
                throw new BadFilterParameterException();
            }
            if (upperLimit < lowerLimit) {
                throw new BadFilterParameterException();
            }
            return new BetweenFileFilter(lowerLimit, upperLimit);
        } else if (filter.equals(SMALLER_THAN)) {  // smaller than filter generator
            double value;
            try {
                value = new Double(commandArray[1]);
            } catch (NumberFormatException e) {
                throw new BadFilterParameterException();
            }
            if (value < 0) {
                throw new BadFilterParameterException();
            }
            return new SmallerThanFileFilter(value);
        } else if (filter.equals(FILE)) {  // file name filter generator
            String fileName;
            try {
                fileName = commandArray[1];
            } catch (IndexOutOfBoundsException e) {
                throw new BadFilterParameterException();
            }
            return new FileNameFileFilter(fileName);
        } else if (filter.equals(CONTAINS)) {  // contains filter generator
            String containsString;
            try {
                containsString = commandArray[1];
            } catch (IndexOutOfBoundsException e) {
                throw new BadFilterParameterException();
            }
            return new ContainsFileFilter(containsString);
        } else if (filter.equals(PREFIX)) {  // prefix filter generator
            String prefix;
            try {
                prefix = commandArray[1];
            } catch (IndexOutOfBoundsException e) {
                throw new BadFilterParameterException();
            }
            return new PrefixFileFilter(prefix);
        } else if (filter.equals(SUFFIX)) {  // suffix filter generator
            String suffix;
            try {
                suffix = commandArray[1];
            } catch (IndexOutOfBoundsException e) {
                throw new BadFilterParameterException();
            }
            return new SuffixFileFilter(suffix);
        } else if (filter.equals(WRITABLE)) {  // writable/non-writable filter generator
            if (commandArray[1] == null) {
                throw new BadFilterParameterException();
            }
            // make sure the second filter parameter is "NOT"
            // TODO: this shouldn't be here.
            if (commandArray.length > 2 && commandArray[2] != null) {
                if (!commandArray[2].equals(NOT)) {
                    throw new BadFilterParameterException();
                }
            }

            // TODO: change the badfilterstate to badfilterparamexception and remove the try block
            try {
                return new WritableFileFilter(commandArray[1]);
            } catch (BadFilterState e) {
                throw new BadFilterParameterException();
            }
        } else if (filter.equals(EXECUTABLE)) {  // executable/non-executable file filter generator
            if (commandArray[1] == null) {
                throw new BadFilterParameterException();
            }

            // make sure the second filter parameter is "NOT"
            // TODO: this shouldn't be here
            if (commandArray.length > 2 && commandArray[2] != null) {
                if (!commandArray[2].equals(NOT)) {
                    throw new BadFilterParameterException();
                }
            }

            // TODO: change the badfilterstate to badfilterparamexception and remove the try block
            try {
                return new ExecutableFileFilter(commandArray[1]);
            } catch (BadFilterState e) {
                throw new BadFilterParameterException();
            }
        } else if (filter.equals(HIDDEN)) {  // hidden/non-hidden file filter generator
            if (commandArray[1] == null) {
                throw new BadFilterParameterException();
            }
            if (commandArray.length > 2 && commandArray[2] != null) {
                if (!commandArray[2].equals(NOT)) {
                    throw new BadFilterParameterException();
                }
            }
            // TODO: change the badfilterstate to badfilterparamexception and remove the try block
            try {
                return new HiddenFileFilter(commandArray[1]);
            } catch (BadFilterState e) {
                throw new BadFilterParameterException();
            }
        } else if (filter.equals(ALL)) {
            return new AllFileFilter();
        }
        throw new BadFilterParameterException();
    }
}
