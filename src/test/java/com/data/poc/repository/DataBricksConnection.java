package com.data.poc.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBricksConnection {
    static final String DB_URL = "jdbc:spark://westus.azuredatabricks.net:443/default;" +
            "transportMode=http;" +
            "ssl=1;" +
            "httpPath=sql/protocolv1/o/1654523072521724/0123-456789-films259;" +
            "AuthMech=3;" +
            "UID=token;" +
            "PWD=<token string>";

    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.simba.spark.jdbc4.Driver");
            connection = DriverManager.getConnection(DB_URL);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

}
