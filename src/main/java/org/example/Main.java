package org.example;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        FileCreate fileCreate = new FileCreate();
        Scanner scanner = new Scanner(System.in);
        Path path = Paths.get("C:\\Users\\pvare\\Desktop\\Music\\newSortTest");
        MyFileVisitor simpleFileVisitor = new MyFileVisitor(fileCreate);
        Set<FileVisitOption> options = new HashSet<>();
        try {
            Files.walkFileTree(path,options,1, simpleFileVisitor);
        } catch (Exception e) {
            System.out.println(e);
        }

        fileCreate.createFiles();
    }
}