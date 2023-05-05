package me.reidj.takiwadai.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class FileManager {

    private static Path path;

    public void createFile(String url) throws IOException {
        path = Paths.get(new File(url).toURI());
        if (!isExists() && !isDir(path)) {
            Files.createFile(path);
        }
    }

    private static boolean isExists() {
        return Files.exists(path);
    }

    private static boolean isDir(Path path) {
        return Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS);
    }

    public void onWrite(byte[] bytes) {
        try {
            Files.write(path, bytes, StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] onRead() {
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
