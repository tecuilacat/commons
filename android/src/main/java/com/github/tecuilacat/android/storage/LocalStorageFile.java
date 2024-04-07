package com.github.tecuilacat.android.storage;

import android.content.Context;
import com.github.tecuilacat.android.filesystem.FileUtils;

import java.time.Instant;
import java.util.Date;

/**
 * An object of this class is assigned to a file in the filesystem <br>
 * Via this class the file can be read or updated / overwritten
 * @since v1.1
 * @author tecuilacat
 */
public class LocalStorageFile {

    private final String fileName;

    private Date lastUpdated;

    public LocalStorageFile(String fileName, Date lastUpdated) {
        this.fileName = fileName;
        this.lastUpdated = lastUpdated;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public String getFileName() {
        return fileName;
    }

    public void setLastUpdated() {
        this.lastUpdated = Date.from(Instant.now());
    }

    /**
     * Stores data to the assigned file. If it is a String, it gets directly written to the file, if it is not a string it first gets parsed to a JSON-object
     * @implNote File gets overwritten entirely - not appended
     * @param context Android context
     * @param obj Object to store in the file
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
