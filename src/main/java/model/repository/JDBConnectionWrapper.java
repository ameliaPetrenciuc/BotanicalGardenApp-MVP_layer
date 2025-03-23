package model.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBConnectionWrapper {
    private static final String JDBC_DRIVER="com.mysql.cj.jdbc.Driver";
    private static final String DB_URL="jdbc:mysql://localhost/";
    private static final String USER="root";
    private static final String PASSWORD="";
    private static final int TIMEOUT=5;
    private Connection connection;

    public JDBConnectionWrapper(String schema){
        try{
            Class.forName(JDBC_DRIVER);
            connection= DriverManager.getConnection(DB_URL+schema, USER,PASSWORD);
            System.out.println("Connected to database: " + schema); // Log connection success
        }catch(ClassNotFoundException e){
            e.printStackTrace();
            System.out.println("FAILURE:" + schema);
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("FAILURE: " + schema);
        }
    }

    public Connection getConnection(){
        return connection;
    }
}
