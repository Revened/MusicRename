package org.example;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileCreate {
    // singleton
    private List<String> list = new ArrayList<>();
    private List<Path> oldPath = new ArrayList<>();
    public void uploadInfo(Path oldPath, String list) {
        this.oldPath.add(oldPath);
        this.list.add(list);
    }

    public void createFiles() {
        try {
            for (int i = 0; i < list.size(); i++) {
                Path path = Path.of(oldPath.get(i).getParent() + "\\" + list.get(i));
                if (oldPath.get(i).toFile().renameTo(path.toFile())) { // Файлы с одинаковым названием созданы не будут
                    System.out.println("Файл: " + ConsoleOutput.ANSI_GREEN + oldPath.get(i) + " был переименован" +  ConsoleOutput.RESET_COLOR + "\t Новое имя: " + path.getFileName());
                } else {
                    System.out.println("Файл: " + ConsoleOutput.ANSI_RED + oldPath.get(i) + " не был переименован" + ConsoleOutput.RESET_COLOR);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
