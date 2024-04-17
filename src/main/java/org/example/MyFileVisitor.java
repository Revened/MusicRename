package org.example;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.helpers.DefaultHandler;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class MyFileVisitor implements FileVisitor {
    //private int cycles = 15; //Важно!! из-за отсутствия лицензии, количество работ с файлами не может превышать 15
    FileCreate fileCreate;

    public MyFileVisitor(FileCreate fileCreate) {
        this.fileCreate = fileCreate;
    }

    @Override
    public FileVisitResult preVisitDirectory(Object dir, BasicFileAttributes attrs) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Object file, BasicFileAttributes attrs) throws IOException {
        if (!attrs.isDirectory()) {
            Path path = Path.of(file.toString());
            if (file.toString().endsWith("mp3")) {
                try (InputStream input = new FileInputStream(path.toFile())) {
                    ContentHandler handler = new DefaultHandler();
                    Metadata metadata = new Metadata();
                    Parser parser = new Mp3Parser();
                    ParseContext parseCtx = new ParseContext();
                    parser.parse(input, handler, metadata, parseCtx);

                    String artist = metadata.get("xmpDM:artist");
                    String songName = metadata.get("title");

                    fileCreate.uploadInfo(path, StringFormatter.formatArtistTitle(artist, songName)); // Отправка инфы для создания файла

                    return FileVisitResult.CONTINUE;
                } catch (Exception e) {
                    System.out.println(ConsoleOutput.ANSI_RED + e + ConsoleOutput.RESET_COLOR);
                    return FileVisitResult.CONTINUE;
                }
            } else { // если формат не mp3
                System.out.println(ConsoleOutput.ANSI_RED + StringFormatter.getFileName(file) + ConsoleOutput.RESET_COLOR + " Имеет неверный формат");
                return FileVisitResult.CONTINUE;
            }
        }
        return FileVisitResult.CONTINUE; // если не файл
    }

    @Override
    public FileVisitResult visitFileFailed(Object file, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Object dir, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }
}
