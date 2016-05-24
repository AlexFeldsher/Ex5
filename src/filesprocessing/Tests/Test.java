package filesprocessing.Tests;

import filesprocessing.DP2;
import filesprocessing.DirectoryProcessor;
import filesprocessing.filefilters.AllFileFilter;
import filesprocessing.filefilters.FileFilterFactory;
import filesprocessing.exceptions.TypeIError;

import java.io.*;
import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        String basicFilterDir = "/home/alex/IdeaProjects/Ex5/basic_filters";
        String basicSourceDir = "/home/alex/IdeaProjects/Ex5/basic_source_directory";
        String advFilterDir = "/home/alex/IdeaProjects/Ex5/advanced_filters";
        String advSourceDir = "/home/alex/IdeaProjects/Ex5/advanced_source_directory";

        // basic test
        File fltDir = new File(basicFilterDir);
        for (File flt : fltDir.listFiles()) {
            String[] arg = {basicSourceDir, flt.getPath()};
            DP2.main(arg);
        }

    }
}
