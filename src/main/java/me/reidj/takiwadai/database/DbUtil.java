package me.reidj.takiwadai.database;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOError;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class DbUtil {

    private static final String DB_USERNAME = "db.username";
    private static final String DB_PASSWORD = "db.password";
    private static final String DB_URL = "db.url";
    private static final String DB_DRIVER_CLASS = "driver.class.name";

    private static HikariDataSource dataSource;

    static {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("src/main/resources/database.properties"));

            dataSource = new HikariDataSource();
            dataSource.setDriverClassName(properties.getProperty(DB_DRIVER_CLASS));

            dataSource.setJdbcUrl(properties.getProperty(DB_URL));
            dataSource.setUsername(properties.getProperty(DB_USERNAME));
            dataSource.setPassword(properties.getProperty(DB_PASSWORD));

            dataSource.setMinimumIdle(100);
            dataSource.setMaximumPoolSize(2000);
            dataSource.setAutoCommit(false);
            dataSource.setLoginTimeout(3);
        } catch (IOError | FileNotFoundException | SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}
