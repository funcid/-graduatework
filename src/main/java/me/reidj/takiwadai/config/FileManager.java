package me.reidj.takiwadai.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class FileManager {

    private static final String URL = "settings.json";

    private static final Path PATH = Paths.get(new File(URL).toURI());

    public void createFile() throws IOException {
        if (!isExists(PATH) && !isDir(PATH)) {
            Files.createFile(PATH);
        }
    }

    public static boolean isExists(Path path) {
        return Files.exists(path);
    }

    public static boolean isDir(Path path) {
        return Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS);
    }

    public void onWrite(byte[] bytes) {
        try {
            Files.write(PATH, bytes, StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] onRead() {
        try {
            return Files.readAllBytes(PATH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
