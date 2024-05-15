## LocalStorageHolder
This part of the module is used to store data to files in the local filesystem. Those files will be found at `/your-app/files/...`

In the following example we are implementing a startup-configuration and want to control whether a pin on startup of the app is desired

`LocalStorageFileNames.java`
```java
/**
 * Class is used to store filenames thus keys
 */
public class LocalStorageFileNames {
    
    public static final String FILENAME_STARTUP_CONFIG = "startupconfig"; // you can use files with or without .txt
    
}
```
<br>

`StartUpConfig.java`

```java
public class StartupConfig {

    private boolean pinDesired;

    private String pin;

    public StartupConfig(boolean pinDesired, String pin) {
        this.pinDesired = pinDesired;
        this.pin = pin;
    }

    public String getPin() {
        return pin;
    }

    public boolean pinDesired() {
        return pinDesired;
    }

    public void setPinDesired(boolean pinDesired) {
        this.pinDesired = pinDesired;
    }
}
```
<br>

`MainActivity.java`
```java
public class MainActivity extends Activity {

    private LocalStorageFile startupConfigStorageFile;
    
    private StartupConfig startupConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startupConfigStorageFile = LocalStorageHolder.register(this, LocalStorageFileNames.FILENAME_STARTUP_CONFIG);
        initStartUpConfig();
        
        startupConfig = startupConfigStorageFile.get(this, StartupConfig.class);
        
        if (startupConfig.pinDesired()) {
            // validate the pin (dialog or something similar)
        }
        
        // forward to the next screen
    }

    /**
     * Init the initial startup config if it is empty (otherwise the app will crash)
     */
    private void initStartUpConfig() {
        if (startupConfigStorageFile.get(this).isEmpty()) {
            StartupConfig startupConfig = new StartupConfig(false, "");
        }
    }

    /**
     * At this point in the program you have filled out a dialog, stated that you want to use a pin and have already entered that pin. This method simply stores that to the filesystem
     */
    private void setPin(String pin) {
        startupConfig.setPinDesired(true);
        startupConfig.setPin(pin);
        startupConfigStorageFile.update(this, startupConfig);
        startupConfig = startupConfigStorageFile.get(this, StartupConfig.class); // update the private field
    }
    
}
```