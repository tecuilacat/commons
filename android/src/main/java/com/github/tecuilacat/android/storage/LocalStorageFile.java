package com.github.tecuilacat.android.storage;

import android.content.Context;
import com.github.tecuilacat.android.filesystem.FileUtlis;

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
        FileUtlis fileUtlis = new FileUtlis(context);
        if (obj instanceof String str) {
            fileUtlis.saveRaw(this.fileName, str);
        } else {
            fileUtlis.save(this.fileName, obj);
        }
        this.setLastUpdated();
    }

    public <T> T get(Context context, Class<T> clazz) {
        FileUtlis fileUtlis = new FileUtlis(context);
        return fileUtlis.getContent(this.fileName, clazz);
    }

    public String get(Context context) {
        FileUtlis fileUtlis = new FileUtlis(context);
        return fileUtlis.getContent(this.fileName);
    }

}
