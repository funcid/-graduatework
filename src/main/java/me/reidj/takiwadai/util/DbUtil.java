package me.reidj.takiwadai.util;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.IOError;
import java.util.Properties;

import static me.reidj.takiwadai.util.Utils.RESOURCES;

public class DbUtil {

    public static final String CREATE_TABLE_USERS = "CREATE TABLE IF NOT EXISTS users (id INT AUTO_INCREMENT, name TEXT, surname TEXT, secondName TEXT, email TEXT, password TEXT, roleType TEXT, PRIMARY KEY(id));";
    public static final String CREATE_TABLE_APPLICATIONS = "CREATE TABLE IF NOT EXISTS applications (id INT AUTO_INCREMENT, userId INT, name TEXT, surname TEXT, secondName TEXT, email TEXT, description TEXT, date TIMESTAMP, category TEXT, status TEXT, PRIMARY KEY (id), FOREIGN KEY(userId) REFERENCES users (id));";
    public static final String CREATE_USER = "INSERT INTO users (name, surname, secondName, email, password, roleType) VALUES(?, ?, ?, ?, ?, ?);";
    public static final String DUPLICATE_USER = "SELECT COUNT(*) FROM users WHERE email = ";
    public static final String UPDATE_USER = "UPDATE users SET name = ?, surname = ?, secondName = ?, password = ? WHERE id = ?";
    public static final String SELECT_USER_BY_EMAIL_AND_PASSWORD = "SELECT * FROM users WHERE email = ? AND password = ?";
    public static final String SELECT_USER_BY_EMAIL = "SELECT * FROM users WHERE email = ?";
    public static final String SELECT_APPLICATION = "SELECT * FROM applications WHERE userId = ?";
    public static final String SELECT_APPLICATIONS = "SELECT * FROM applications";
    public static final String CREATE_APPLICATION = "INSERT INTO applications (userId, name, surname, secondName, email, description, date, category, status) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);";
    public static final String DELETE_APPLICATION = "DELETE FROM applications WHERE id = ?";
    public static final String UPDATE_APPLICATION_STATUS = "UPDATE applications SET status = ? WHERE id = ?";
    public static final String UPDATE_USER_PASSWORD = "UPDATE users SET password = ? WHERE email = ?";

    private static final String DB_USERNAME = "db.username";
    private static final String DB_PASSWORD = "db.password";
    private static final String DB_URL = "db.url";
    private static final String DB_DRIVER_CLASS = "driver.class.name";

    private static HikariDataSource dataSource;

    static {
        try {
            Properties properties = Utils.loadPropertyFile(RESOURCES + "database.properties");

            dataSource = new HikariDataSource();
            dataSource.setDriverClassName(properties.getProperty(DB_DRIVER_CLASS));

            dataSource.setJdbcUrl(properties.getProperty(DB_URL));
            dataSource.setUsername(properties.getProperty(DB_USERNAME));
            dataSource.setPassword(properties.getProperty(DB_PASSWORD));

            dataSource.setIdleTimeout(600000);
            dataSource.setMaximumPoolSize(250);
            dataSource.setMinimumIdle(3);
        } catch (IOError e) {
            e.printStackTrace();
        }
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}
