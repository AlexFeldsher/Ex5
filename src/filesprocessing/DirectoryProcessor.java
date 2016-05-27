package filesprocessing;

import filesprocessing.comperators.FileComparatorFactory;
import filesprocessing.comperators.FileNameComparator;
import filesprocessing.exceptions.*;
import filesprocessing.filefilters.AllFileFilter;
import filesprocessing.filefilters.FileFilterFactory;

import java.io.*;
import java.util.*;

/**
 * Directory processor class. Returns a list of files of a given source directory filtered and sorted
 * according to a given filter command file.
 */
public class DirectoryProcessor {

    /* default filter and comparator */
    private final FileFilter DEFAULT_FILE_FILTER = new AllFileFilter();
    private final Comparator<File> DEFAULT_FILE_COMPARATOR = new FileNameComparator();

    /* subsection constants */
    private final int MISSING_SUBSECTION = 1;
    private final int BAD_SUBSECTION_NAME = -1;
    private final int VALID_SUBSECTION = 0;

    /* Error messages */
    private static final String BAD_SUBSECTION_ERR_MSG = "ERROR: Bad subsection name";
    private static final String IO_EXCEPTION_MSG = "ERROR: IO exception";
    private static final String BAD_FILE_FORMAT_MSG = "ERROR: Bad format of Commands File";
    private static final String WARNING_MSG = "Warning in line ";

    /* source dir */
    private final File SOURCE_DIR;
    private final String FILTER_FILE_PATH;

    /* flags */
    private boolean notFlag;
    private boolean reverseFlag;

    /* counter for the current filter file line number */
    private int currentLineNumber;

    /* filter parameters */
    private final String NOT_PARAM = "NOT";
    private final String REVERSE_PARAM = "REVERSE";


    public static void main(String[] args) {
        String sourceDirPath = args[0];
        String filterFilePath = args[1];
        DirectoryProcessor dp;
        ArrayList<File> fileList;
        try {
            dp = new DirectoryProcessor(filterFilePath, sourceDirPath);
            fileList = dp.processDirectory();
        } catch (IOException e) {
            System.err.println(IO_EXCEPTION_MSG);
            System.err.flush();
            return;
        } catch (BadFilterFileFormatException e) {
            System.err.println(BAD_FILE_FORMAT_MSG);
            System.err.flush();
            return;
        } catch (BadSubSectionNameException e) {
            System.err.println(BAD_SUBSECTION_ERR_MSG);
            System.err.flush();
            return;
        }

        for (File file : fileList) {
            System.out.println(file.getName());
            System.out.flush();
        }
    }

    /**
     * Directory Processor constructor
     *
     * @param filterFilePath path to the filter file
     * @param sourceDirPath  path to the source directory
     * @throws IOException          IO error with the filter file
     * @throws BadFilterFileFormatException invalid filter file format
     */
    public DirectoryProcessor(String filterFilePath, String sourceDirPath) throws IOException,
            BadFilterFileFormatException {
        SOURCE_DIR = new File(sourceDirPath);
        FILTER_FILE_PATH = filterFilePath;
        notFlag = false;
        reverseFlag = false;
        currentLineNumber = 0;
        // validate the filter file
        boolean filterValid = validateFilterFileStructure();
        if (!filterValid) {
            throw new BadFilterFileFormatException();
        }
    }

    /**
     * Processes the source directory with the filter file.
     *
     * @return array list of filtered and sorted file objects
     * @throws IOException                IO error
     * @throws BadSubSectionNameException filter file has bad subsection name (FILTER/ORDER)
     * @throws BadFilterFileFormatException       filter file has a bad format (missing FILTER or ORDER)
     */
    public ArrayList<File> processDirectory() throws IOException, BadSubSectionNameException,
            BadFilterFileFormatException {
        FileReader filterFileReader = new FileReader(FILTER_FILE_PATH);
        BufferedReader bufferedFilterReader = new BufferedReader(filterFileReader);
        IterableFilterFile iterableFilterFile = new IterableFilterFile(bufferedFilterReader);
        ArrayList<File> fileList = new ArrayList<>();

        try {
            for (String[] filterBlock : iterableFilterFile) {
                ArrayList<File> blockFileList = processBlock(filterBlock);
                fileList.addAll(blockFileList);
            }
        } catch (RuntimeException e) {
            // RuntimeException is thrown in the iterator when there's an IOException
            throw new IOException(e);
        }
        return fileList;
    }

    /*
     * Receives a FILTER-ORDER block and returns a filtered and sorted file array list
     * @param filterFileBlock a FILTER-ORDER block
     * @return filtered and sorted file array list
     * @throws BadFilterFileFormatException
     * @throws BadSubSectionNameException
     */
    private ArrayList<File> processBlock(String[] filterFileBlock) throws BadFilterFileFormatException,
            BadSubSectionNameException {
        String filterSubSection = filterFileBlock[0];
        String filterCommand = filterFileBlock[1];
        String orderSubSection = filterFileBlock[2];
        String orderCommand = filterFileBlock[3];
        FileFilter fileFilter;
        Comparator<File> fileComparator;

        currentLineNumber++;

        // handle first list
        int validFilterSec = validateFilterSubSection(filterSubSection);
        switch (validFilterSec) {
            case MISSING_SUBSECTION:
                throw new BadFilterFileFormatException();
            case BAD_SUBSECTION_NAME:
                throw new BadSubSectionNameException();
        }

        // handle second line
        try {
            fileFilter = generateFileFilter(filterCommand);
            currentLineNumber++;
        } catch (NullPointerException e) {
            fileFilter = DEFAULT_FILE_FILTER;
        } catch (BadFilterParameterException e) {
            fileFilter = DEFAULT_FILE_FILTER;
            currentLineNumber++;
            System.err.println(WARNING_MSG + currentLineNumber);
            System.err.flush();
        }

        // handle third line
        currentLineNumber++;
        int validOrderSec = validateOrderSubSection(orderSubSection);
        switch (validOrderSec) {
            case MISSING_SUBSECTION:
                throw new BadFilterFileFormatException();
            case BAD_SUBSECTION_NAME:
                throw new BadSubSectionNameException();
        }

        // handle fourth line
        try {
            fileComparator = generateFileComparator(orderCommand);
            currentLineNumber++;
        } catch (NullPointerException e) {
            fileComparator = DEFAULT_FILE_COMPARATOR;
        } catch (BadFilterParameterException e) {
            fileComparator = DEFAULT_FILE_COMPARATOR;
            currentLineNumber++;
            System.err.println(WARNING_MSG + currentLineNumber);
            System.err.flush();
        }

        ArrayList<File> fileList = filterFiles(fileFilter);
        orderFileArrayList(fileList, fileComparator);  // sorts fileList itself

        return fileList;
    }

    /*
     * Create an array list of filtered files
     * @param fileFilter filter to use
     * @return array list of filtered files
     */
    private ArrayList<File> filterFiles(FileFilter fileFilter) {
        ArrayList<File> fileList = new ArrayList<>();
        // filter files
        for (File f : SOURCE_DIR.listFiles(fileFilter)) {
            fileList.add(f);
        }
        // handle a NOT command
        if (notFlag) {
            ArrayList<File> notFileList = new ArrayList<>();
            for (File file1 : SOURCE_DIR.listFiles()) {
                boolean addFlag = true;
                for (File file2 : fileList) {
                    String fileName1 = file1.getName();
                    String fileName2 = file2.getName();
                    if (fileName1.equals(fileName2)) {
                        addFlag = false;
                    }
                }
                if (addFlag && file1.isFile()) {
                    notFileList.add(file1);
                }
            }
            fileList = notFileList;
        }
        return fileList;
    }


    /*
     * Sorts a file list with a given comparator and handles a REVERSE command.
     * IMPORTANT: sorts the fileList given as a parameter.
     * @param fileList file list to sort
     * @param fileComparator comparator to sort with
     */
    private void orderFileArrayList(ArrayList<File> fileList, Comparator<File> fileComparator) {
        if (fileList.size() > 1) {
            // handle REVERSE command
            if (reverseFlag) {
                fileList.sort(fileComparator);
                Collections.reverse(fileList);
            } else {
                fileList.sort(fileComparator);
            }
        }
    }

    /*
     * Verifies the Filter subsection is valid.
     * @param filterSubSection filter subsection string.
     * @return 0 if valid, 1 if missing, -1 if bad subsection name
     */
    private int validateFilterSubSection(String filterSubSection) {
        if (filterSubSection == null) {
            return MISSING_SUBSECTION;
        }
        if (!filterSubSection.equals(IterableFilterFile.FILTER_SUBSECTION_NAME)) {
            return BAD_SUBSECTION_NAME;
        }

        return VALID_SUBSECTION;
    }

    /*
     * Verifies the order subsection is valid.
     * @param orderSubSection order subsection string.
     * @return VALID_SUBSECTION if valid, MISSING_SUBSECTION if missing,
      * BAD_SUBSECTION_NAME if bad subsection name
     */
    private int validateOrderSubSection(String orderSubSection) {
        if (orderSubSection == null) {
            return MISSING_SUBSECTION;
        }
        if (!orderSubSection.equals(IterableFilterFile.ORDER_SUBSECTION_NAME)) {
            return BAD_SUBSECTION_NAME;
        }

        return VALID_SUBSECTION;
    }


    /*
     * Generates a file filter according to a given filter command string.
     * @param filterCommand filter command string.
     * @return file filter.
     */
    private FileFilter generateFileFilter(String filterCommand) throws BadFilterParameterException,
            NullPointerException {
        String[] splitFilterCommand = filterCommand.split("#");
        // true if last filter command parameter is "NOT", otherwise false
        notFlag = splitFilterCommand[splitFilterCommand.length - 1].equals(NOT_PARAM) ? true : false;
        return FileFilterFactory.select(filterCommand);
    }


    /*
     * Generates a file comparator according to a given order command string.
     * @param orderCommand order command string.
     * @return file comparator.
     */
    private Comparator<File> generateFileComparator(String orderCommand) throws BadFilterParameterException,
            NullPointerException {
        String[] splitOrderCommand = orderCommand.split("#");
        // true if last order command parameter is "REVERSE", otherwise false
        reverseFlag = splitOrderCommand[splitOrderCommand.length - 1].equals(REVERSE_PARAM) ? true : false;
        return FileComparatorFactory.select(splitOrderCommand[0]);
    }

    /*
     * Validate the format of the given filter file
     * @param filterFilePath path to a filter file
     * @return true if valid, otherwise false.
     * @throws IOException
     */
    private boolean validateFilterFileStructure() throws IOException {
        try (FileReader filterFileReader = new FileReader(FILTER_FILE_PATH);
             BufferedReader bufferedFilterFileReader = new BufferedReader(filterFileReader);) {
            IterableFilterFile iterableFilterFile = new IterableFilterFile(bufferedFilterFileReader);
            for (String[] commandBlock : iterableFilterFile) {
                if (commandBlock[2] == null) {
                    return false;
                }
                if (!commandBlock[0].equals(IterableFilterFile.FILTER_SUBSECTION_NAME)) {
                    return false;
                }
                if (!commandBlock[2].equals(IterableFilterFile.ORDER_SUBSECTION_NAME)) {
                    return false;
                }
            }
        }
        return true;
    }
}
