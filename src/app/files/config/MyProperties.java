package app.files.config;

import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;


/**
 * 
 */
public class MyProperties extends Properties {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Path of properties file
     */
    private final String propFile = Paths.get("").toAbsolutePath().toString() + "\\src\\app\\files\\config\\configs.properties";
    

    public MyProperties() {
        super();
        this.loadProperties();
    }

    /**
     * Load properties file
     */
    public void loadProperties() {
        
        File f =  new File(this.propFile);
        try (FileInputStream in = new FileInputStream(f)) {
            this.load(in);
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}