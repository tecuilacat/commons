package com.github.tecuilacat.android.filesystem;

import android.content.Context;
import android.util.Log;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Utility class for managing local storage files.
 * <p>
 * This class provides methods for registering, removing, and retrieving local storage files,
 * as well as accessing information about registered storage files.
 * </p>
 * <ul>
 *      <li>API documentation can be found <a href="https://github.com/tecuilacat/commons/blob/master/android/README.md">here</a></li>
 * </ul>
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

    /**
     * Serializes an object to JSON and saves it to a file with the specified filename.
     * <p>
     * This method uses an {@code ObjectMapper} to convert the provided object into a JSON string.
     * It then writes the JSON string to the specified file. If a {@code JsonProcessingException}
     * occurs during serialization, an error message is logged.
     * </p>
     *
     * @param filename the name of the file to save the JSON content to
     * @param obj the object to be serialized and saved
     */
    public void save(String filename, Object obj) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
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

    /**
     * Writes a JSON string to a file with the specified filename.
     * <p>
     * This method opens a file output stream in private mode and writes the provided JSON string to the file.
     * If an I/O error occurs during the write operation, an error message is logged.
     * </p>
     *
     * @param filename the name of the file to write to
     * @param json the JSON string to be written to the file
     */
    private void write(String filename, String json) {
        try (FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE)) {
            fos.write(json.getBytes());
        } catch (IOException ioe) {
            Log.e(FileUtils.class.getName(), "Could not write to file " + filename, ioe);
        }
    }

    /**
     * Reads the content of a file with the specified filename and returns it as a string.
     * <p>
     * This method opens a file input stream, reads the content of the file into a byte array,
     * and converts it to a string. If an I/O error occurs during the read operation, an error
     * message is logged, and the method returns {@code null}.
     * </p>
     *
     * @param filename the name of the file to read from
     * @return the content of the file as a string, or {@code null} if an error occurs
     */
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

    /**
     * Reads the content of a file and converts it to an object of the specified type.
     * <p>
     * This method reads the content of the file with the given filename as a JSON string
     * and then deserializes it into an object of the specified class type using the {@code getJsonAsObject} method.
     * If an error occurs during either the file read or JSON deserialization process, the method returns {@code null}.
     * </p>
     *
     * @param <T> the type of the object to be returned
     * @param filename the name of the file to read from
     * @param clazz the class of the type into which the file content should be deserialized
     * @return the deserialized object of type {@code T}, or {@code null} if an error occurs
     */
    public <T> T getContent(String filename, Class<T> clazz) {
        String json = read(filename);
        return getJsonAsObject(json, clazz);
    }

    /**
     * Converts a JSON string to an object of the specified type.
     * <p>
     * This method uses an {@code ObjectMapper} to deserialize the provided JSON string into an object of the given class type.
     * If a {@code JsonProcessingException} occurs during deserialization, an error message is logged, and the method returns {@code null}.
     * </p>
     *
     * @param <T> the type of the object to be returned
     * @param json the JSON string to be deserialized
     * @param clazz the class of the type into which the JSON string should be deserialized
     * @return the deserialized object of type {@code T}, or {@code null} if an error occurs
     */
    public  <T> T getJsonAsObject(String json, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException jpe) {
            Log.e(FileUtils.class.getName(), "Could not read json as type " + clazz.getName(), jpe);
        }
        return null;
    }

}
