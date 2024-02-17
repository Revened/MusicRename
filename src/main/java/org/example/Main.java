package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        FileCreate fileCreate = new FileCreate();
        Path path = Paths.get("C:\\Users\\pvare\\Desktop\\Music\\newSortTest");
        MyFileVisitor simpleFileVisitor = new MyFileVisitor(fileCreate);
        try {
            Files.walkFileTree(path, simpleFileVisitor);
        } catch (Exception e) {
            System.out.println(e);
        }

        fileCreate.createFiles();
    }
}