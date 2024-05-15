package com.github.tecuilacat.android.storage;

import android.content.Context;
import com.github.tecuilacat.android.filesystem.FileUtils;

import java.time.Instant;
import java.util.Date;

/**
 * Represents a file in local storage.
 * <p>
 * This class provides methods to interact with a file in local storage,
 * including updating its content, retrieving data from it, and accessing metadata.
 * </p>
 * @since v1.1
 * @author tecuilacat
 */
public class LocalStorageFile {

    private final String fileName;

    private Date lastUpdated;

    /**
     * Constructs a new LocalStorageFile object with the specified filename and last updated date.
     *
     * @param fileName the name of the file
     * @param lastUpdated the date when the file was last updated
     */
    public LocalStorageFile(String fileName, Date lastUpdated) {
        this.fileName = fileName;
        this.lastUpdated = lastUpdated;
    }

    /**
     * Retrieves the date when the file was last updated.
     *
     * @return the date when the file was last updated
     */
    public Date getLastUpdated() {
        return lastUpdated;
    }

    /**
     * Retrieves the filename of the file.
     *
     * @return the filename of the file
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets the last updated timestamp to the current date and time.
     */
    public void setLastUpdated() {
        this.lastUpdated = Date.from(Instant.now());
    }

    /**
     * Updates the content of the file associated with this instance using the provided object.
     * <p>
     * This method checks the type of the provided object. If the object is a {@code String},
     * it saves the raw string content to the file. Otherwise, it serializes the object
     * and saves it to the file. After updating the file, the method sets the last updated timestamp.
     * </p>
     *
     * @param context the application context used to access the file system
     * @param obj the object to be saved to the file; can be either a {@code String} or any other serializable object
     * @since v1.1
     */
    public void update(Context context, Object obj) {
        FileUtils fileUtils = new FileUtils(context);
        if (obj instanceof String) {
            fileUtils.saveRaw(this.fileName, (String) obj);
        } else {
            fileUtils.save(this.fileName, obj);
        }
        this.setLastUpdated();
    }

    /**
     * Retrieves data from the file assigned to this object and parses it to the declared type
     * @param context Android context
     * @param clazz Data type the data gets parsed to
     * @return Data from the file parsed to a declared type
     * @param <T> Any object that is represented by the data stored in the file
     * @since v1.1
     */
    public <T> T get(Context context, Class<T> clazz) {
        FileUtils fileUtils = new FileUtils(context);
        return fileUtils.getContent(this.fileName, clazz);
    }

    /**
     * Retrieves data from the file assigned to this object and returns the data as raw string
     * @param context Android context
     * @return Data as string
     * @since v1.1
     */
    public String get(Context context) {
        FileUtils fileUtils = new FileUtils(context);
        return fileUtils.getContent(this.fileName);
    }

}
