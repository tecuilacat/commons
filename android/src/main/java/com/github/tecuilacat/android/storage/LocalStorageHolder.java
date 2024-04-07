package com.github.tecuilacat.android.storage;

import android.content.Context;
import com.github.tecuilacat.android.filesystem.FileUtils;

import java.io.File;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class holds all files in which saved data is stored. From this handler files can be retrieved and data can be read or stored
 * @implNote For better usability and to prevent errors, use an extra class solely for filenames / keys
 * @since v1.1
 * @see LocalStorageFile
 * @author tecuilacat
 */
public class LocalStorageHolder {

    private static final Map<String, LocalStorageFile> registeredStorageFiles = new HashMap<>();

    private LocalStorageHolder() { }

    /**
     * Registers a file to the static holder and stores it in a static context
     * @implNote Any file you register will be in any other class anywhere in your application during runtime since it is held in a static context<br>
     *           Read documentation <a href="https://github.com/tecuilacat/commons/tree/master/android/src/main/java/com/github/tecuilacat/android/storage/README.md">here</a>
     * @param context Android context
     * @param filename Actual filename you want to use in the local storage
     * @return The either newly created or the already stored LocalStorageFile-object in the holder
     * @since v1.1
     */
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
        registeredStorageFiles.put(filename.toLowerCase(), new LocalStorageFile(filename, lastModified));
        return registeredStorageFiles.get(filename.toLowerCase());
    }

    /**
     * @param localStorageFile Removes the object from the LocalStorageHolder if present
     */
    public static void removeStorageFile(LocalStorageFile localStorageFile) {
        if (registeredStorageFiles.containsKey(localStorageFile.getFileName()) || registeredStorageFiles.containsValue(localStorageFile)) {
            registeredStorageFiles.remove(localStorageFile.getFileName());
        }
    }

    /**
     * @param filename Actual name of the file in the filesystem (or key in the map)
     */
    public static void removeStorageFile(String filename) {
        registeredStorageFiles.remove(filename.toLowerCase());
    }

    /**
     * @return All filenames managed by the LocalStorageHolder in lower case
     */
    public static List<String> getStorageFileNames() {
        return new ArrayList<>(registeredStorageFiles.keySet());
    }

    /**
     * @param filename Actual filename (or key in the map)
     * @return LocalStorageFile if held by the LocalStorageHolder, empty if not
     */
    public static Optional<LocalStorageFile> get(String filename) {
        return registeredStorageFiles.values()
                .stream()
                .filter(rsf -> rsf.getFileName().equalsIgnoreCase(filename))
                .findFirst();
    }

}
