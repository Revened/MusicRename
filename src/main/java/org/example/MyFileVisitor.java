package org.example;

import com.groupdocs.metadata.Metadata;
import com.groupdocs.metadata.core.MP3RootPackage;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class MyFileVisitor implements FileVisitor {
    private int cycles = 15; //Важно!! из-за отсутствия лицензии, количество работ с файлами не может превышать 15
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
            while (cycles-- != 0) {
                String path = file.toString();
                if (file.toString().endsWith("mp3")) {
                    try (Metadata metadata = new Metadata(path)) {              // Поток для считывания инфы о песне
                        MP3RootPackage song = metadata.getRootPackageGeneric();
                        Path oldFile = Path.of(path);
                        String artist;
                        try {
                            if (song.getID3V1() != null) {              // ID3V1
                                artist = song.getID3V1().getArtist();
                                fileCreate.uploadInfo(oldFile, StringFormatter.formatArtistSong(artist, path)); // Форматирование строки и обновление информации для последующего создания/изменения файла

                            } else if (song.getID3V2() != null) {       // ID3V2
                                artist = song.getID3V2().getArtist();
                                fileCreate.uploadInfo(oldFile, StringFormatter.formatArtistSong(artist, path)); // Форматирование строки и обновление информации для последующего создания/изменения файла

                            } else if (song.getLyrics3V2() != null) {   // Lyrics3V2
                                artist = song.getLyrics3V2().getArtist();
                                fileCreate.uploadInfo(oldFile, StringFormatter.formatArtistSong(artist, path)); // Форматирование строки и обновление информации для последующего создания/изменения файла

                            } else if (song.getApeV2() != null) {       // ApeV2
                                artist = song.getApeV2().getArtist();
                                fileCreate.uploadInfo(oldFile, StringFormatter.formatArtistSong(artist, path)); // Форматирование строки и обновление информации для последующего создания/изменения файла

                            }
                        } catch (NullPointerException e) { // В случае отсутствия артиста или других параметров
                            System.out.println(ConsoleOutput.ANSI_RED + StringFormatter.getFileName(file) + ConsoleOutput.RESET_COLOR +  " Отсутствует артист");
                        } finally {
                            return FileVisitResult.CONTINUE;
                        }
                    } catch (Exception e) {                 //  В случае возникновений ошибок в работе metadata
                        System.out.println(ConsoleOutput.ANSI_RED + e + ConsoleOutput.RESET_COLOR);
                        return FileVisitResult.CONTINUE;
                    }
                } else { // если формат не mp3
                    System.out.println(ConsoleOutput.ANSI_RED + StringFormatter.getFileName(file) + ConsoleOutput.RESET_COLOR +  " Имеет неверный формат");
                    return FileVisitResult.CONTINUE;
                }

            }
            return FileVisitResult.TERMINATE; // ограничение в 15 файлов
        } return FileVisitResult.CONTINUE; // если не файл
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
