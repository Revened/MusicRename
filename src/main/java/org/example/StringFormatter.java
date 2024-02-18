package org.example;

import java.nio.file.Path;

public class StringFormatter {
    public static void IsArtistNeeded(boolean isArtistNeeded) {
        StringFormatter.isArtistNeeded = isArtistNeeded;
    }
    private static boolean isArtistNeeded = true;
    private static String pathToNameSong(Path path) {
        String str = path.toString();
        String[] actor_song = str.split("\\\\");
        String[] array = actor_song[actor_song.length - 1].split("-");
        StringBuilder songName = new StringBuilder();
        if (array.length > 2) {                     // Если в названии песни есть знак -
            for (int i = 1; i < array.length; i++) {
                songName.append(array[i] + (array[i].contains("mp3") ? "":"-"));
            }
        } else songName.append(array[1]);           // Если нет знака -
        return songName.toString();    //  название песни;
    }
    public static String formatArtistSong(String artist, Path songPath) {       // использовать если нет в файле название песни
        String song = pathToNameSong(songPath);                                 // в таком случае будет использоваться имя файла
        if (isArtistNeeded) {
            if (artist.contains("/")) {                                       //
                String[] array = artist.split("/");                     // Если присутствуют два автора разделенные ; \ /
                artist = array[0] + ", " + array[1];                          //
            }
            return artist + " -" + song; // имя артиста + название песни
        } else {
            return song;
        }
    }
    public static String formatArtistSong(String artist, String songName) {     // использовать если есть в файле название песни
        if (isArtistNeeded) {
            if (artist.contains("/")) {                                       //
                String[] array = artist.split("/");                     // Если присутствуют два автора разделенные ; \ /
                artist = array[0] + ", " + array[1];                          //
            }
            return artist + " - " + songName + ".mp3"; // имя артиста + название песни
        } else {
            return songName + ".mp3";
        }
    }
    public static String formatSongArtist(String artist, Path songPath) {
        String song = pathToNameSong(songPath);
        return song + " -" + artist; // название песни + имя артиста
    }
    public static String getFileName(Object path) {
        String stringPath = path.toString();
        String[] array = stringPath.split("\\\\");
        return array[array.length - 1];
    }
}
