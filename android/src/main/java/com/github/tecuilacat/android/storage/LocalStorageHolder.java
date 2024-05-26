package com.github.tecuilacat.android.storage;

import android.content.Context;
import com.github.tecuilacat.android.filesystem.FileUtils;

import java.io.File;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Utility class for managing local storage files.
 * <p>
 * This class provides methods for registering, removing, and retrieving local storage files,
 * as well as accessing information about registered storage files.
 * </p>
 * <ul>
 *         <li>Class documentation can be found <a href="https://github.com/tecuilacat/commons/blob/master/android/src/main/java/com/github/tecuilacat/android/storage/README.md">here</a></li>
 *         <li>API documentation can be found <a href="https://github.com/tecuilacat/commons/blob/master/android/README.md">here</a></li>
 * </ul>
 * @implNote For better usability and to prevent errors, use an extra class solely for filenames / keys
 * @see LocalStorageFile
 * @author tecuilacat
 * @since v1.1
 */
public class LocalStorageHolder {

    private static final Map<String, LocalStorageFile> registeredStorageFiles = new HashMap<>();

    private LocalStorageHolder() { }

    /**
     * Registers a local storage file with the given filename in the application context.
     * <p>
     * This method checks if the file already exists in the application's project folder.
     * If the file does not exist, it creates a new empty file with the given filename.
     * If the file exists, it retrieves the last modified date of the file.
     * The file is then registered in a static map with the filename as the key and a {@code LocalStorageFile} object as the value.
     * </p>
     *
     * @param context the application context used to access the file system
     * @param filename the name of the file to be registered
     * @return the registered {@code LocalStorageFile} object
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
     * Removes a {@code LocalStorageFile} from the registered storage files.
     * <p>
     * This method checks if the specified {@code LocalStorageFile} exists in the registered storage files map
     * either by its filename or by its reference. If found, the file is removed from the map.
     * </p>
     *
     * @param localStorageFile the {@code LocalStorageFile} object to be removed
     * @since v1.1
     */
    public static void removeStorageFile(LocalStorageFile localStorageFile) {
        if (registeredStorageFiles.containsKey(localStorageFile.getFileName()) || registeredStorageFiles.containsValue(localStorageFile)) {
            registeredStorageFiles.remove(localStorageFile.getFileName());
        }
    }

    /**
     * Removes a registered storage file with the specified filename.
     * <p>
     * This method removes the registered storage file from the map using the specified filename
     * in a case-insensitive manner.
     * </p>
     *
     * @param filename the filename of the storage file to be removed
     * @since v1.1
     */
    public static void removeStorageFile(String filename) {
        registeredStorageFiles.remove(filename.toLowerCase());
    }

    /**
     * Retrieves a list of filenames of all registered storage files.
     * <p>
     * This method returns a list containing the filenames of all registered storage files.
     * </p>
     *
     * @return a list of filenames of all registered storage files
     * @since 1.1
     */
    public static List<String> getStorageFileNames() {
        return new ArrayList<>(registeredStorageFiles.keySet());
    }

    /**
     * Retrieves a registered {@code LocalStorageFile} object by its filename.
     * <p>
     * This method searches for a {@code LocalStorageFile} object with the specified filename
     * in the registered storage files map. If found, it returns an {@code Optional} containing
     * the {@code LocalStorageFile} object; otherwise, it returns an empty {@code Optional}.
     * </p>
     *
     * @param filename the filename of the {@code LocalStorageFile} to retrieve
     * @return an {@code Optional} containing the {@code LocalStorageFile} object if found,
     *         or an empty {@code Optional} otherwise
     * @since v1.1
     */
    public static Optional<LocalStorageFile> get(String filename) {
        return registeredStorageFiles.values()
                .stream()
                .filter(rsf -> rsf.getFileName().equalsIgnoreCase(filename))
                .findFirst();
    }

}
