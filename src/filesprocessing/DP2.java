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

public class DP2 implements Iterable<String[]> {

    private FileReader fileReader;

    public static void main(String[] args) {
        String sourceDirPath = args[0];
        String filterFilePath = args[1];

        File temp = new File(filterFilePath);
        String filterName = temp.getName();

        File sourceDir = new File(sourceDirPath);
        DP2 dp2 = new DP2(filterFilePath);
        System.out.println("***********************");
        System.out.println(sourceDirPath + "\n" + filterFilePath);
        System.out.println("-----------------------");
        int lineCounter = 0;
        FileFilter fileFilter;
        Comparator<File> fileComparator;
        for (String[] commandBlock : dp2) {
            ArrayList<File> fileList = new ArrayList<>();
            boolean notFlag = false;
            boolean reverseFlag = false;

            // handle line 1
            lineCounter++;
            if (commandBlock[0] == null) {
                System.err.print(filterName + " ERROR: Bad format of Commands File\n");
                return;
            }
            if (!commandBlock[0].equals("FILTER")) {
                System.err.print(filterName + " ERROR: Bad subsection name\n");
                return;
            }

            // handle line 2
            lineCounter++;
            try {
                String[] splitFilterCommand = commandBlock[1].split("#");
                notFlag = splitFilterCommand[splitFilterCommand.length - 1].equals("NOT") ? true : false;
                fileFilter = FileFilterFactory.select(commandBlock[1]);
            } catch (TypeIError e) {
                System.err.print(filterName + " Warning in line " + lineCounter + "\n");
                fileFilter = new AllFileFilter();
            } catch (BadCommandFileFormat e) {
                System.err.print(filterName + " ERROR: Bad format of Commands File\n");
                return;
            } catch (NullPointerException e) {
                fileFilter = new AllFileFilter();
            }

            // handle line 3
            lineCounter++;
            if (commandBlock[2] == null) {
                System.err.print(filterName + " ERROR: Bad format of Commands File\n");
                return;
            }
            if (!commandBlock[2].equals("ORDER")) {
                System.err.print(filterName + " ERROR: Bad subsection name\n");
                return;
            }

            // handle line 4
            lineCounter++;
            try {
                String[] splitOrderCommand = commandBlock[3].split("#");
                reverseFlag = splitOrderCommand[splitOrderCommand.length - 1].equals("REVERSE") ? true : false;
                fileComparator = FileComparatorFactory.select(splitOrderCommand[0]);
            } catch (NullPointerException e) {
                System.err.print(filterName + " Warning in line " + lineCounter + "\n");
                fileComparator = new FileNameComparator();
            } catch (TypeIError e) {
                System.err.print(filterName + " Warning in line " + lineCounter + "\n");
                fileComparator = new FileNameComparator();
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
                    if (addFlag) {
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

            // print final list
            for (File f : fileList) {
                System.out.println(f.getName());
            }
        }
    }

    public DP2(String filterFilePath) {
        try {
            fileReader = new FileReader(filterFilePath);
        } catch (FileNotFoundException e) {
            // TODO: handle exception
        }
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
