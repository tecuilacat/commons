package com.github.tecuilacat.android.filesystem;

import android.content.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtlis {

    private final Logger LOGGER = LogManager.getLogger(FileUtlis.class);

    private final Context context;

    public FileUtlis(Context context) {
        this.context = context;
    }

    public void save(String filename, Object obj) {
        LOGGER.debug("Attempting to convert object ({}) to JSON and saving it to file ({})", obj, filename);
        ObjectMapper mapper = new ObjectMapper();
        try {
            String contentAsJson = mapper.writeValueAsString(obj);
            write(filename, contentAsJson);
        } catch (JsonProcessingException jpe) {
            LOGGER.error("Could not parse object to JSON", jpe);
        }
    }

    private void write(String filename, String json) {
        try (FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE)) {
            fos.write(json.getBytes());
        } catch (IOException ioe) {
            LOGGER.error("Could not open OutputStream", ioe);
        }
    }

    private String read(String filename) {
        try (FileInputStream fis = context.openFileInput(filename)) {
            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer); //NOSONAR
            return new String(buffer);
        } catch (IOException ioe) {
            LOGGER.error("Could not open InputStream", ioe);
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
            LOGGER.error("Could not parse JSON to object", jpe);
        }
        return null;
    }

}
