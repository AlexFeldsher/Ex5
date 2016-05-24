package filesprocessing.Tests;

import filesprocessing.DirectoryProcessor;

import java.io.*;

public class Test {
    public static void main(String[] args) {
        String s = "/home/alex/IdeaProjects/Ex5/advanced_filters/filter050.flt";
        Test.advTestSingle(s);
    }

    public static void advTestSingle(String filter) {
        String advSourceDir = "/home/alex/IdeaProjects/Ex5/advanced_source_directory";
        String[] com = {advSourceDir, filter};
        DirectoryProcessor.main(com);

    }

    public static void anvTest() {
        String advFilterDir = "/home/alex/IdeaProjects/Ex5/advanced_filters";
        String advSourceDir = "/home/alex/IdeaProjects/Ex5/advanced_source_directory";
        File fltDir = new File(advFilterDir);
        for (File flt : fltDir.listFiles()) {
            System.out.println("--------------------------");
            System.out.println(flt.getName());
            System.out.println("--------------------------");
            String[] arg = {advSourceDir, flt.getPath()};
            DirectoryProcessor.main(arg);
            System.out.println("==========================");
        }
    }

    public static void basicTest() {
        String basicFilterDir = "/home/alex/IdeaProjects/Ex5/basic_filters";
        String basicSourceDir = "/home/alex/IdeaProjects/Ex5/basic_source_directory";

        // single test
        String s = "/home/alex/IdeaProjects/Ex5/basic_filters/filter038.flt";
        String[] com = {basicSourceDir, s};
        DirectoryProcessor.main(com);


        System.out.println("=================================");
        System.out.println("=================================");
        // basic test

    }
}
