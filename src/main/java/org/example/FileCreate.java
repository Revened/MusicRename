package org.example;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileCreate {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
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
                if (oldPath.get(i).toFile().renameTo(path.toFile())) {
                    System.out.println(ANSI_GREEN + "Файл: " + oldPath.get(i) + " был переименован" +  ANSI_RESET + "\t Новое имя: " + path.getFileName());
                } else {
                    System.out.println(ANSI_RED + "Файл: " + oldPath.get(i) + " не был переименован" + ANSI_RESET);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
