package database;

import static database.Constants.Tables.*;

public class SQLTableCreationFactory {

    public String getCreateSQLForTable(String table) {
        return switch (table) {
            case PLANT -> "CREATE TABLE IF NOT EXISTS plant (" +
                    "  id INT AUTO_INCREMENT PRIMARY KEY," +
                    "  name VARCHAR(255) NOT NULL," +
                    "  species VARCHAR(255) NOT NULL," +
                    "  type VARCHAR(255) NOT NULL," +
                    "  carnivorous BOOLEAN NOT NULL," +
                    "  image VARCHAR(500)" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";

            case EXEMPLARY -> "CREATE TABLE IF NOT EXISTS exemplary (" +
                    "  id INT AUTO_INCREMENT PRIMARY KEY," +
                    "  plant_id INT NOT NULL," +
                    "  zone VARCHAR(255) NOT NULL," +
                    "  image VARCHAR(500)," +
                    "  FOREIGN KEY (plant_id) REFERENCES plant(id) " +
                    "    ON DELETE CASCADE ON UPDATE CASCADE" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";

            default -> "";
        };
    }
}
