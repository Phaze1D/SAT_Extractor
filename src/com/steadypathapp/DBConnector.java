package com.steadypathapp;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 * IntelliJ IDEA.
 * User: davidvillarreal
 * Date: 6/26/15
 * Time: 2:05 PM
 */
public class DBConnector {

    private static DBConnector singletonInstance;
    private MysqlDataSource dataSource;


    private DBConnector() {


    }

    public static DBConnector getInstance(){
        if (singletonInstance == null){
            singletonInstance = new DBConnector();
        }

        return singletonInstance;
    }

    /**
     * Creates a connection to the database
     * @param user The user name
     * @param password The user's password
     */
    public void connect(String user, String password) throws SQLException {
        Connection con = null;
        dataSource = new MysqlDataSource();
        dataSource.setServerName("nyrda");
        dataSource.setPortNumber(3306);
        dataSource.setDatabaseName("VFFacturas");
        dataSource.setUser(user);
        dataSource.setPassword(password);

        con = dataSource.getConnection();
        DatabaseMetaData meta = con.getMetaData();
        System.out.println("Server name: "
                + meta.getDatabaseProductName());
        System.out.println("Server version: "
                + meta.getDatabaseProductVersion());


        con.close();
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}

