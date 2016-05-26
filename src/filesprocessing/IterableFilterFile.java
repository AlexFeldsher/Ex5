package filesprocessing;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class IterableFilterFile implements Iterable<String[]> {

    /* Hold the filter file buffered reader */
    private final BufferedReader BUFFERED_FILTER_FILE_READER;

    public IterableFilterFile(BufferedReader bufferedFilterFileReader) {
        BUFFERED_FILTER_FILE_READER = bufferedFilterFileReader;

    }

    public Iterator<String[]> iterator() {
        class FilterBlockIterator implements Iterator<String[]> {

            /* Holds the next block to be returned */
            private String[] block;
            /* Hold the next block first value, in case of a missing line in the filter file */
            private String nextBlockFirstLine;

            public boolean hasNext() {
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
                    try {
                        String line = BUFFERED_FILTER_FILE_READER.readLine();
                        if (line == null) {
                            break;
                        }
                        if (i == 1 && line.equals("ORDER")) {
                            block[i] = null;
                            i++;
                            block[i] = line;
                        } else if (i == 3 && line.equals("FILTER")) {
                            block[i] = null;
                            nextBlockFirstLine = line;
                        } else {
                            block[i] = line;
                        }
                    } catch (IOException e) {
                        // TODO: handle exception
                    }
                    i++;
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
