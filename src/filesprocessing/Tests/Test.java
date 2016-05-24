package filesprocessing.Tests;

import filesprocessing.DirectoryProcessor;

import java.io.*;

public class Test {
    public static void main(String[] args) {
        String basicFilterDir = "/home/alex/IdeaProjects/Ex5/basic_filters";
        String basicSourceDir = "/home/alex/IdeaProjects/Ex5/basic_source_directory";
        String advFilterDir = "/home/alex/IdeaProjects/Ex5/advanced_filters";
        String advSourceDir = "/home/alex/IdeaProjects/Ex5/advanced_source_directory";

        // single test
        String s = basicFilterDir + "/filter031.flt";
        String[] com = {basicFilterDir, s};
        DirectoryProcessor.main(com);


        System.out.println("=================================");
        System.out.println("=================================");
        // basic test
        File fltDir = new File(basicFilterDir);
        for (File flt : fltDir.listFiles()) {
            String[] arg = {basicSourceDir, flt.getPath()};
            DirectoryProcessor.main(arg);
        }

    }
}
