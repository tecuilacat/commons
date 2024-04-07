package com.github.tecuilacat.android.filesystem;

import android.content.Context;
import android.util.Log;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class manages the communication to the internal file system of the app. All files edited by this class are going to be in /your-app/files/...
 * @implNote Usage of this class in raw is not recommended for larger implementation. Rather use com.github.tecuilacat.android.storage.LocalStorageHolder
 * @see com.github.tecuilacat.android.storage.LocalStorageHolder
 * @since v1.1
 * @author tecuilacat
 */
public class FileUtils {

    private final Context context;

    public FileUtils(Context context) {
        this.context = context;
    }

    public void save(String filename, Object obj) {

        ObjectMapper mapper = new ObjectMapper();
        try {
            String contentAsJson = mapper.writeValueAsString(obj);
            write(filename, contentAsJson);
        } catch (JsonProcessingException jpe) {
            Log.e(FileUtils.class.getName(), "Could not map object to json", jpe);
        }
    }

    public void saveRaw(String filename, String str) {
        write(filename, str);
    }

    private void write(String filename, String json) {
        try (FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE)) {
            fos.write(json.getBytes());
        } catch (IOException ioe) {
            Log.e(FileUtils.class.getName(), "Could not write to file " + filename, ioe);
        }
    }

    private String read(String filename) {
        try (FileInputStream fis = context.openFileInput(filename)) {
            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer); //NOSONAR
            return new String(buffer);
        } catch (IOException ioe) {
            Log.e(FileUtils.class.getName(), "Could not read from file " + filename, ioe);
        }
        return null;
    }

    public String getContent(String filename) {
        return read(filename);
    }

    public <T> T getContent(String filename, Class<T> clazz) {
        String json = read(filename);
        return getJsonAsObject(json, clazz);
    }

    public  <T> T getJsonAsObject(String json, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException jpe) {
            Log.e(FileUtils.class.getName(), "Could not read json as type " + clazz.getName(), jpe);
        }
        return null;
    }

}
