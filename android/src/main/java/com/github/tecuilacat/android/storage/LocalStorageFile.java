package com.github.tecuilacat.android.storage;

import android.content.Context;
import com.github.tecuilacat.android.filesystem.FileUtils;

import java.time.Instant;
import java.util.Date;

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
     * @implNote File gets overwritten entirely
     * @param context Android context
     * @param obj Object to store in the file
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

    public <T> T get(Context context, Class<T> clazz) {
        FileUtils fileUtils = new FileUtils(context);
        return fileUtils.getContent(this.fileName, clazz);
    }

    public String get(Context context) {
        FileUtils fileUtils = new FileUtils(context);
        return fileUtils.getContent(this.fileName);
    }

}
