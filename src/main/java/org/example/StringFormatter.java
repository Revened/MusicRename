package org.example;

import java.nio.file.Path;

public class StringFormatter {
    private static String pathToNameSong(String str) {
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
    public static String formatArtistSong(String artist, String songPath) {
        if (artist.contains("/")) {                                       //
            String[] array = artist.split("/");                     // Если присутствуют несколько авторов
            artist = array[0] + ", " + array[1];                          //
        }
        String song = pathToNameSong(songPath);
        return artist + " -" + song; // имя артиста + название песни
    }
    public static String formatSongArtist(String artist, String songPath) {
        String song = pathToNameSong(songPath);
        return song + " -" + artist; // название песни + имя артиста
    }
    public static String getFileName(Object path) {
        String stringPath = path.toString();
        String[] array = stringPath.split("\\\\");
        return array[array.length - 1];
    }
}
