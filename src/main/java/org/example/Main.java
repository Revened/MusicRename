package org.example;

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
        System.out.println("Введите полный путь до требуемых файлов");
        Path path = Paths.get(scanner.nextLine());

        StringFormatter.IsArtistNeeded(true);
        StringFormatter.IsTitleNeeded(true);

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