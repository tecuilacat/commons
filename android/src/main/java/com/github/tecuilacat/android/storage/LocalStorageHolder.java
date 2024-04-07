package com.github.tecuilacat.android.storage;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class LocalStorageHolder {

    private static Map<String, LocalStorageFile> registeredStorageFiles;

    private LocalStorageHolder() { }

    public static void init() {
        registeredStorageFiles = new HashMap<>();
    }

    public static LocalStorageFile register(String filename) throws IOException {
        File file = new File(filename);
        Date lastModified = Date.from(Instant.now());
        if (!file.isFile()) {
            try {
                file.createNewFile();
            } catch (IOException ioe) {
                throw new IOException("Storagefile " + filename + " could not be created", ioe);
            }
        } else {
            lastModified = new Date(file.lastModified());
        }
        LocalStorageFile localStorageFile = new LocalStorageFile(filename, lastModified);
        registeredStorageFiles.put(filename, localStorageFile);
        return localStorageFile;
    }

    public static void removeStorageFile(LocalStorageFile localStorageFile) {
        if (registeredStorageFiles.containsKey(localStorageFile.getFileName()) || registeredStorageFiles.containsValue(localStorageFile)) {
            registeredStorageFiles.remove(localStorageFile.getFileName());
        }
    }

    public static void removeStorageFile(String filename) {
        registeredStorageFiles.remove(filename);
    }

    public static List<String> getStorageFileNames() {
        return new ArrayList<>(registeredStorageFiles.keySet());
    }

    public static Optional<LocalStorageFile> get(String filename) {
        return registeredStorageFiles.values()
                .stream()
                .filter(rsf -> rsf.getFileName().equalsIgnoreCase(filename))
                .findFirst();
    }

}
