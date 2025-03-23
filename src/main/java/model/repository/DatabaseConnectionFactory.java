package model.repository;

import model.repository.JDBConnectionWrapper;

public class DatabaseConnectionFactory {
    private static final String SCHEMA = "botanic_garden";

    public static JDBConnectionWrapper getConnectionWrapper() {
        return new JDBConnectionWrapper(SCHEMA);
    }
}
