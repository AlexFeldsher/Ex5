package filesprocessing.Tests;

import filesprocessing.DirectoryProcessor;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

public class Test {
    public static void main(String[] args) {
        String s = "/cs/stud/feld/IdeaProjects/Ex5/advanced_filters/filter059.flt";
        String basicFilter = "/cs/stud/feld/IdeaProjects/Ex5/basic_filters/filter023.flt";
        Test.advTestSingle(s);
//        Test.basicTestSingle(basicFilter);
//        Test.filenameSortTest();
    }

    public static void advTestSingle(String filter) {
        String advSourceDir = "/cs/stud/feld/IdeaProjects/Ex5/advanced_source_directory";
        String[] com = {advSourceDir, filter};
        DirectoryProcessor.main(com);

    }

    public static void anvTest() {
        String advFilterDir = "/cs/stud/feld/IdeaProjects/Ex5/advanced_filters";
        String advSourceDir = "/cs/stud/feld/IdeaProjects/Ex5/advanced_source_directory";
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

    public static void basicTestSingle(String filter) {
        String basicSourceDir = "/cs/stud/feld/IdeaProjects/Ex5/basic_source_directory";
        // single test
        String[] com = {basicSourceDir, filter};
        DirectoryProcessor.main(com);
    }

    public static void filenameSortTest() {
        String[] s = {"file2.txt", "same_name_c.txt"};
        ArrayList<String> names = new ArrayList<>();
        for (String str : s) {
            names.add(str);
        }
        class StringComparator implements Comparator<String> {
            public int compare(String s1, String s2) {
                return s1.compareTo(s2);
            }
        }
        names.sort(new StringComparator());
        for (String str : names) {
            System.out.println(str);
        }
    }
}
