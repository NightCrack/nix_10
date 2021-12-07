package ua.com.alevel.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public final class FileOps {

    private FileOps() {
    }

    public static void createDirs(String... paths) {
        for (String path : paths) {
            Path current = Paths.get(path);
            try {
                if (!Files.exists(current)) {
                    Files.createDirectories(current);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createFiles(String... paths) {
        for (String path : paths) {
            Path current = Paths.get(path);
            try {
                if (!Files.exists(current)) {
                    Files.createFile(current);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void write(String path, String value, boolean append) {
        try {
            FileWriter fileWriter = new FileWriter(path, append);
            fileWriter.write(value);
            fileWriter.flush();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static List<String> read(String path) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        Stream<String> lines = bufferedReader.lines();
        return lines.toList();
    }
}
