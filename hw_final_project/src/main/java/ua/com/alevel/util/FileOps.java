package ua.com.alevel.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

public final class FileOps {

    private FileOps() {
    }

    public static boolean createDirs(String... paths) {
        for (String path : paths) {
            Path current = Paths.get(path);
            try {
                if (!Files.exists(current)) {
                    Files.createDirectories(current);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public static boolean createFiles(String... paths) {
        for (String path : paths) {
            Path current = Paths.get(path);
            try {
                if (!Files.exists(current)) {
                    Files.createFile(current);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public static boolean write(String path, String value, boolean append) {
        try {
            FileWriter fileWriter = new FileWriter(path, append);
            fileWriter.write(value);
            fileWriter.flush();
            return true;
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public static <Element extends Collection<String>> Element read(String path) {
        return read(path, 0, Integer.MAX_VALUE);
    }

    public static <Element extends Collection<String>> Element read(String path, int startLine) {
        return read(path, startLine, Integer.MAX_VALUE);
    }

    public static <Element extends Collection<String>> Element read(String path, int startLine, int endLine) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            ArrayList<String> returnList = new ArrayList<>();
            int lineIndex = 0;
            while (bufferedReader.ready()) {
                if (startLine > 0) {
                    bufferedReader.readLine();
                    startLine--;
                    lineIndex++;
                } else {
                    if (lineIndex < endLine) {
                        returnList.add(bufferedReader.readLine());
                        lineIndex++;
                    } else {
                        break;
                    }
                }
            }
            return (Element) returnList;
        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static boolean isEmpty(String path) {
        File file = new File(path);
        return (file.length() == 0);
    }
}
