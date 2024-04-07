package com.github.tecuilacat.android.storage;

import android.content.Context;
import com.github.tecuilacat.android.filesystem.FileUtils;

import java.io.File;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class LocalStorageHolder {

    private static final Map<String, LocalStorageFile> registeredStorageFiles = new HashMap<>();

    private LocalStorageHolder() { }

    public static LocalStorageFile register(Context context, String filename) {
        List<String> filesInProjectFolder = Arrays.stream(context.fileList()).collect(Collectors.toList());
        Date lastModified = Date.from(Instant.now());
        boolean fileExists = false;

        for (String fileInProjectFolder: filesInProjectFolder) {
            if (fileInProjectFolder.equalsIgnoreCase(filename)) {
                fileExists = true;
                break;
            }
        }

        if (!fileExists) {
            new FileUtils(context).saveRaw(filename, "");
        } else {
            lastModified = new Date(new File(filename).lastModified());
        }
        return registeredStorageFiles.put(filename.toLowerCase(), new LocalStorageFile(filename, lastModified));
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
