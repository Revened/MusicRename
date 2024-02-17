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
        while (cycles-- != 0) {
            String path = file.toString();
            try (Metadata metadata = new Metadata(path)) {              // Поток для считывания инфы о песне
                MP3RootPackage song = metadata.getRootPackageGeneric();
                Path oldFile = Path.of(path);
                String artist;

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
                return FileVisitResult.CONTINUE;
            }
        }
        return FileVisitResult.TERMINATE;

    }

    @Override
    public FileVisitResult visitFileFailed(Object file, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Object dir, IOException exc) throws IOException {
        return FileVisitResult.TERMINATE;
    }
}
