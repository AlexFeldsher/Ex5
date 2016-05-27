package filesprocessing;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterable filter file class. Iterates FILTER-ORDER over blocks of 4 cell string arrays.<br>
 * Null cell when the line is missing in the filter file.
 */
public class IterableFilterFile implements Iterable<String[]> {

    /* Hold the filter file buffered reader */
    private final BufferedReader BUFFERED_FILTER_FILE_READER;

    /**
     * Filter subsection name constant
     */
    static final String FILTER_SUBSECTION_NAME = "FILTER";
    /**
     * Order subsection name constant
     */
    static final String ORDER_SUBSECTION_NAME = "ORDER";

    /**
     * Iterable filter file constructor class
     *
     * @param bufferedFilterFileReader buffered reader of a filter file.
     */
    public IterableFilterFile(BufferedReader bufferedFilterFileReader) {
        BUFFERED_FILTER_FILE_READER = bufferedFilterFileReader;

    }

    /**
     * Returns iterator over FILTER-ORDER blocks a filter file
     * @return iterator over FILTER-ORDER blocks
     */
    public Iterator<String[]> iterator() {
        /* Iterator class that returns FILTER-ORDER blocks in a string array of size 4 */
        class FilterBlockIterator implements Iterator<String[]> {

            /* Holds the next block to be returned */
            private String[] block;
            /* Hold the next block first value, in case of a missing line in the filter file */
            private String nextBlockFirstLine;

            /*
             * Generates the next FILTER-ORDER block to return.
             * If the fist cell is null then there no more blocks to generate.
             */
            private void generateNextBlock() throws IOException {
                block = new String[4];
                int i;
                // verify first node wasn't found in previous iteration
                if (nextBlockFirstLine != null) {
                    block[0] = nextBlockFirstLine;
                    nextBlockFirstLine = null;
                    i = 1;
                } else {
                    i = 0;
                }
                // Generate the next block
                while (i < 4) {
                    String line = BUFFERED_FILTER_FILE_READER.readLine();
                    if (line == null) {
                        break;
                    }
                    if (i == 1 && line.equals(ORDER_SUBSECTION_NAME)) {
                        block[i] = null;
                        i++;
                        block[i] = line;
                    } else if (i == 3 && line.equals(FILTER_SUBSECTION_NAME)) {
                        block[i] = null;
                        nextBlockFirstLine = line;
                    } else {
                        block[i] = line;
                    }
                    i++;
                }
            }

            public boolean hasNext() {
                // a new block was generated if the first block cell isn't null
                // return true if a new block was generated
                return (block[0] == null) ? false : true;
            }

            public String[] next() throws NoSuchElementException {
                // filter file buffered reader can throw an IOException
                try {
                    generateNextBlock();
                } catch (IOException e) {
                    // Throw a runtime exception
                    throw new RuntimeException(e);
                }

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
