package filesprocessing;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import filesprocessing.comperators.FileComparatorFactory;
import filesprocessing.comperators.FileNameComparator;
import filesprocessing.exceptions.*;
import filesprocessing.filefilters.AllFileFilter;
import filesprocessing.filefilters.FileFilterFactory;

import java.io.*;
import java.util.*;

public class DirectoryProcessor {

    /* default filter and comparator */
    private final FileFilter DEFAULT_FILE_FILTER = new AllFileFilter();
    private final Comparator<File> DEFAULT_FILE_COMPARATOR = new FileNameComparator();
    /* subsection constants */
    private final int MISSING_SUBSECTION = 1;
    private final int BAD_SUBSECTION_NAME = -1;
    private final int VALID_SUBSECTION = 0;
    private final String FILTER_SUBSECTION_NAME = "FILTER";
    private final String ORDER_SUBSECTION_NAME = "ORDER";

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
            return;
        } catch (BadCommandFileFormat e) {
            System.err.println(BAD_FILE_FORMAT_MSG);
            return;
        } catch (BadSubSectionNameException e) {
            System.err.println(BAD_SUBSECTION_ERR_MSG);
            return;
        }

        for (File file : fileList) {
            System.out.println(file.getName());
        }
        /*
        // handles invalid subsection names
        try {
            if (!dp.validateFilterFileStructure(filterFilePath)) {
                System.err.print(BAD_SUBSECTION_ERR_MSG);
                System.err.flush();
                return;
            }
        } catch (IOException e) {
            System.err.print(IO_EXCEPTION_MSG);
            return;
        }

        /* closed loop
        File temp = new File(filterFilePath);
        String filterName = temp.getName();
        */

        /*
        // source dir object
        File sourceDir = new File(sourceDirPath);
        // final files list object
        ArrayList<File> orderFileslist = new ArrayList<>();
        // line counter
        int lineCounter = 0;

        // file filter
        FileFilter fileFilter;
        // file comparator
        Comparator<File> fileComparator;

        */
        /*
        // loop over directory processor object
        for (String[] commandBlock : dp) {
            // block file list
            ArrayList<File> fileList = new ArrayList<>();

            // not command was used flag
            boolean notFlag = false;
            // reverse command was used flag
            boolean reverseFlag = false;

            // handle line 1
            lineCounter++;
            if (commandBlock[0] == null) {
                System.err.print("ERROR: Bad format of Commands File\n");
                System.err.flush();
                return;
            }
            if (!commandBlock[0].equals("FILTER")) {
                System.err.print("ERROR: Bad subsection name\n");
                System.err.flush();
                return;
            }

            // handle line 2
            if (commandBlock[1] != null) {
                lineCounter++;
            }
            try {
                String[] splitFilterCommand = commandBlock[1].split("#");
                notFlag = splitFilterCommand[splitFilterCommand.length - 1].equals("NOT") ? true : false;
                fileFilter = FileFilterFactory.select(commandBlock[1]);
            } catch (BadFilterParameterException e) {
                System.err.print("Warning in line " + lineCounter + "\n");
                System.err.flush();
                notFlag = false;
                fileFilter = new AllFileFilter();
            } catch (NullPointerException e) {
                notFlag = false;
                fileFilter = new AllFileFilter();
            }

            // handle line 3
            lineCounter++;
            if (commandBlock[2] == null) {
                System.err.print("ERROR: Bad format of Commands File\n");
                System.err.flush();
                return;
            }
            if (!commandBlock[2].equals("ORDER")) {
                System.err.print("ERROR: Bad subsection name\n");
                System.err.flush();
                return;
            }

            // handle line 4
            lineCounter++;
            if (commandBlock[3] == null) {
                fileComparator = new FileNameComparator();
                lineCounter--;
            } else {
                try {
                    String[] splitOrderCommand = commandBlock[3].split("#");
                    reverseFlag = splitOrderCommand[splitOrderCommand.length - 1].equals("REVERSE") ? true : false;
                    fileComparator = FileComparatorFactory.select(splitOrderCommand[0]);
                } catch (NullPointerException e) {
                    System.err.print("Warning in line " + lineCounter + "\n");
                    System.err.flush();
                    fileComparator = new FileNameComparator();
                } catch (BadOrderTypeException e) {
                    System.err.print("Warning in line " + lineCounter + "\n");
                    System.err.flush();
                    fileComparator = new FileNameComparator();
                }
            }

            // filter files
            for (File f : sourceDir.listFiles(fileFilter)) {
                    fileList.add(f);
            }
            if (notFlag) {
                ArrayList<File> notFileList = new ArrayList<>();
                for (File f : sourceDir.listFiles()) {
                    boolean addFlag = true;
                    for (File f2 : fileList) {
                        if (f.getName().equals(f2.getName())) {
                            addFlag = false;
                        }
                    }
                    if (addFlag && f.isFile()) {
                        notFileList.add(f);
                    }
                }
                fileList = notFileList;
            }

            // order files
            if (fileList.size() > 1) {
                if (reverseFlag) {
                    fileList.sort(fileComparator);
                    Collections.reverse(fileList);
                } else {
                    fileList.sort(fileComparator);
                }
            }

            for (File f : fileList) {
                orderFileslist.add(f);
            }
            // print final list
            /*
            for (File f : fileList) {
                System.out.println(f.getName());
            }
        }
        // print files
        for (File f : orderFileslist) {
            System.out.println(f.getName());
        }
        */
    }

    /**
     * Directory Processor constructor
     *
     * @param filterFilePath path to the filter file
     * @param sourceDirPath  path to the source directory
     * @throws IOException          IO error with the filter file
     * @throws BadCommandFileFormat invalid filter file format
     */
    public DirectoryProcessor(String filterFilePath, String sourceDirPath) throws IOException,
            BadCommandFileFormat {
        SOURCE_DIR = new File(sourceDirPath);
        FILTER_FILE_PATH = filterFilePath;
        notFlag = false;
        reverseFlag = false;
        currentLineNumber = 0;
        boolean filterValid = validateFilterFileStructure();
        if (!filterValid) {
            throw new BadCommandFileFormat();
        }
    }

    /**
     * Processes the source directory with the filter file.
     *
     * @return array list of filtered and sorted file objects
     * @throws IOException                IO error
     * @throws BadSubSectionNameException filter file has bad subsection name (FILTER/ORDER)
     * @throws BadCommandFileFormat       filter file has a bad format (missing FILTER or ORDER)
     */
    public ArrayList<File> processDirectory() throws IOException, BadSubSectionNameException,
            BadCommandFileFormat {
        FileReader filterFileReader = new FileReader(FILTER_FILE_PATH);
        BufferedReader bufferedFilterReader = new BufferedReader(filterFileReader);
        IterableFilterFile iterableFilterFile = new IterableFilterFile(bufferedFilterReader);
        ArrayList<File> fileList = new ArrayList<>();

        for (String[] filterBlock : iterableFilterFile) {
            ArrayList<File> blockFileList = processBlock(filterBlock);
            fileList.addAll(blockFileList);
        }
        return fileList;

        /*
        } catch (IOException e) {
            // TODO: handle exception
            System.err.println(BAD_FILE_FORMAT_MSG);
            return;
        } catch (BadSubSectionNameException e) {
            // TODO: err
        } catch (BadCommandFileFormat e) {
            System.err.println(BAD_FILE_FORMAT_MSG);
        }
        */
    }

    /*
     * Receives a FILTER-ORDER block and returns a filtered and sorted file array list
     * @param filterFileBlock a FILTER-ORDER block
     * @return filtered and sorted file array list
     * @throws BadCommandFileFormat
     * @throws BadSubSectionNameException
     */
    private ArrayList<File> processBlock(String[] filterFileBlock) throws BadCommandFileFormat,
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
                throw new BadCommandFileFormat();
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
        }

        // handle third line
        currentLineNumber++;
        int validOrderSec = validateOrderSubSection(orderSubSection);
        switch (validOrderSec) {
            case MISSING_SUBSECTION:
                throw new BadCommandFileFormat();
            case BAD_SUBSECTION_NAME:
                throw new BadSubSectionNameException();
        }

        // handle fourth line
        try {
            fileComparator = generateFileComparator(orderCommand);
            currentLineNumber++;
        } catch (NullPointerException e) {
            fileComparator = DEFAULT_FILE_COMPARATOR;
        } catch (BadOrderTypeException e) {
            fileComparator = DEFAULT_FILE_COMPARATOR;
            currentLineNumber++;
            System.err.println(WARNING_MSG + currentLineNumber);
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
        if (filterSubSection.equals(FILTER_SUBSECTION_NAME)) {
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
        if (orderSubSection.equals(ORDER_SUBSECTION_NAME)) {
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
        notFlag = splitFilterCommand[splitFilterCommand.length - 1].equals("NOT") ? true : false;
        return FileFilterFactory.select(filterCommand);
    }


    /*
     * Generates a file comparator according to a given order command string.
     * @param orderCommand order command string.
     * @return file comparator.
     */
    private Comparator<File> generateFileComparator(String orderCommand) throws BadOrderTypeException,
            NullPointerException {
        String[] splitOrderCommand = orderCommand.split("#");
        // true if last order command parameter is "REVERSE", otherwise false
        reverseFlag = splitOrderCommand[splitOrderCommand.length - 1].equals("REVERSE") ? true : false;
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
                if (!commandBlock[0].equals(FILTER_SUBSECTION_NAME)) {
                    return false;
                }
                if (!commandBlock[2].equals(ORDER_SUBSECTION_NAME)) {
                    return false;
                }
            }
        }
        return true;
    }
}
