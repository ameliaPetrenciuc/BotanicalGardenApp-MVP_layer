package database;

import model.repository.JDBConnectionWrapper;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import static database.Constants.Schemas.PRODUCTION;
import static database.Constants.Tables.*;

public class Bootstrap {
    public static void main(String[] args) throws SQLException {
        dropAll();
        bootstrapTables();
    }

    private static void dropAll() throws SQLException {
        System.out.println("Dropping all tables in schema: " + PRODUCTION);

        Connection connection = new JDBConnectionWrapper(PRODUCTION).getConnection();
        Statement statement = connection.createStatement();

        String[] dropStatements = {
                "SET FOREIGN_KEY_CHECKS=0;",
                "DROP TABLE IF EXISTS `" + EXEMPLARY + "`;",
                "DROP TABLE IF EXISTS `" + PLANT + "`;",
                "SET FOREIGN_KEY_CHECKS=1;"
        };

        Arrays.stream(dropStatements).forEach(dropStatement -> {
            try {
                statement.execute(dropStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        System.out.println("Tables dropped successfully.");
    }

    private static void bootstrapTables() throws SQLException {
        SQLTableCreationFactory sqlTableCreationFactory = new SQLTableCreationFactory();
        System.out.println("Bootstrapping " + PRODUCTION + " schema");

        JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper(PRODUCTION);
        Connection connection = connectionWrapper.getConnection();

        Statement statement = connection.createStatement();

        String[] orderedTables = {PLANT, EXEMPLARY};

        for (String table : orderedTables) {
            String createTableSQL = sqlTableCreationFactory.getCreateSQLForTable(table);
            statement.execute(createTableSQL);
            System.out.println("Created table: " + table);
        }

        System.out.println("Database schema setup complete.");
    }
}
