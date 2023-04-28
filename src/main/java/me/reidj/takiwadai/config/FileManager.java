package me.reidj.takiwadai.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class FileManager {

    private static final String URL = "settings.json";

    private static final Path PATH = Paths.get(new File(URL).toURI());

    public void createFile() throws IOException {
        if (!isExists() && !isDir()) {
            Files.createFile(PATH);
        }
    }

    private static boolean isExists() {
        return Files.exists(FileManager.PATH);
    }

    private static boolean isDir() {
        return Files.isDirectory(FileManager.PATH, LinkOption.NOFOLLOW_LINKS);
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
