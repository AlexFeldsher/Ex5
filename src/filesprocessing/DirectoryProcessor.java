package filesprocessing;

import filesprocessing.comperators.FileComparatorFactory;
import filesprocessing.comperators.FileNameComparator;
import filesprocessing.exceptions.BadCommandFileFormat;
import filesprocessing.exceptions.BadSubSectionNameException;
import filesprocessing.exceptions.TypeIError;
import filesprocessing.filefilters.AllFileFilter;
import filesprocessing.filefilters.FileFilterFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class DirectoryProcessor implements Iterable<String[]> {

    private FileReader fileReader;

    public static void main(String[] args) {
        String sourceDirPath = args[0];
        String filterFilePath = args[1];
        DirectoryProcessor dp = new DirectoryProcessor(filterFilePath);
        // handles invalid subsection names
        try {
            if (!dp.validateCommandsFileStructure(filterFilePath)) {
                System.err.print("ERROR: Bad subsection name\n");
                return;
            }
        } catch (IOException e) {
            // TODO: handle exception
        }

        File temp = new File(filterFilePath);
        String filterName = temp.getName();

        File sourceDir = new File(sourceDirPath);
        ArrayList<File> orderFileslist = new ArrayList<>();
        /*
        System.out.println("***********************");
        System.out.println(sourceDirPath + "\n" + filterFilePath);
        System.out.println("-----------------------");
        */
        int lineCounter = 0;
        FileFilter fileFilter;
        Comparator<File> fileComparator;

        for (String[] commandBlock : dp) {
            ArrayList<File> fileList = new ArrayList<>();
            boolean notFlag = false;
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
            lineCounter++;
            try {
                String[] splitFilterCommand = commandBlock[1].split("#");
                notFlag = splitFilterCommand[splitFilterCommand.length - 1].equals("NOT") ? true : false;
                fileFilter = FileFilterFactory.select(commandBlock[1]);
            } catch (TypeIError e) {
                System.err.print("Warning in line " + lineCounter + "\n");
                System.err.flush();
                notFlag = false;
                fileFilter = new AllFileFilter();
            } catch (BadCommandFileFormat e) {
                System.err.print("ERROR: Bad format of Commands File\n");
                System.err.flush();
                return;
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
            } else {
                try {
                    String[] splitOrderCommand = commandBlock[3].split("#");
                    reverseFlag = splitOrderCommand[splitOrderCommand.length - 1].equals("REVERSE") ? true : false;
                    fileComparator = FileComparatorFactory.select(splitOrderCommand[0]);
                } catch (NullPointerException e) {
                    System.err.print("Warning in line " + lineCounter + "\n");
                    System.err.flush();
                    fileComparator = new FileNameComparator();
                } catch (TypeIError e) {
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
                    fileList.sort(fileComparator.reversed());
                } else {
                    fileList.sort(fileComparator);
                }
            }

            orderFileslist.addAll(fileList);
            // print final list
            /*
            for (File f : fileList) {
                System.out.println(f.getName());
            }
            */
        }
        // print files
        for (File f : orderFileslist) {
            System.out.println(f.getName());
        }
    }

    public DirectoryProcessor(String filterFilePath) {
        try {
            fileReader = new FileReader(filterFilePath);
        } catch (FileNotFoundException e) {
            // TODO: handle exception
        }
    }

    public boolean validateCommandsFileStructure(String commandFilePath) throws IOException {
        DirectoryProcessor dp = new DirectoryProcessor(commandFilePath);
        for (String[] commandBlock : dp) {
            if (commandBlock[2] == null) {
                return false;
            }
            if (!commandBlock[0].equals("FILTER")) {
                return false;
            }
            if (!commandBlock[2].equals("ORDER")) {
                return false;
            }
        }
        return true;
    }

    public Iterator<String[]> iterator() {
        LineNumberReader lineNumberReader = new LineNumberReader(fileReader);

        class FilterBlockIterator implements Iterator<String[]> {
            private String[] block;

            public boolean hasNext() {
                block = new String[4];
                // Generate the next block
                for (int i = 0; i < 4; i++) {
                    try {
                        String line = lineNumberReader.readLine();
                        if (line != null) {
                            block[i] = line;
                        }
                    } catch (IOException e) {
                        // TODO: handle exception
                    }
                }
                // return true if a new block was generated
                return (block[0] == null) ? false : true;
            }

            public String[] next() throws NoSuchElementException {
                if (block[0] != null) {
                    return block;
                }
                throw new NoSuchElementException();
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        }

        return new FilterBlockIterator();
    }
}
