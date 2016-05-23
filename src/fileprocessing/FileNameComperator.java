package fileprocessing;

import java.io.File;
import java.util.Comparator;

public class FileNameComperator implements Comparator<File> {
    public int compare(File f1, File f2) {
        return f1.getName().compareTo(f2.getName());
    }
}
