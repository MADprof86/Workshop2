package pl.coderslab.TaskManager;

import javax.imageio.IIOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/workshop2?useSSL=false&characterEncoding=utf8";
    private static  String DB_USER;
    private static  String DB_PASS;

    private static void loadConfig() throws IOException {
        Properties prop = new Properties();
        try(InputStream input = new FileInputStream("src/main/resources/config.properties");){

            prop.load(input);
            DB_USER = prop.getProperty("db.username");
            DB_PASS = prop.getProperty("db.password");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public DbUtil(){
        try {
            loadConfig();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static Connection getConnection(){
        try {
            loadConfig();
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
