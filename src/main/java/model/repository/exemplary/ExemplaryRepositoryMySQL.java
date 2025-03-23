package model.repository.exemplary;

import model.Exemplary;
import model.Plant;
import model.repository.DatabaseConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExemplaryRepositoryMySQL implements ExemplaryRepository {
    private final Connection connection;

    public ExemplaryRepositoryMySQL() {
        this.connection = DatabaseConnectionFactory.getConnectionWrapper().getConnection();
    }

    @Override
    public boolean add(Exemplary exemplary) {
        String sql = "INSERT INTO exemplary (plant_id, zone, image) VALUES (?, ?, ?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, exemplary.getPlantId());
            preparedStatement.setString(2, exemplary.getZone());
            preparedStatement.setString(3, exemplary.getImage());

            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Exemplary exemplary, Integer plant_id, String zone, String image) {
        String sql = "UPDATE exemplary SET plant_id = ?, zone = ?, image = ? WHERE id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, plant_id);
            preparedStatement.setString(2, zone);
            preparedStatement.setString(3, image);
            preparedStatement.setInt(4, exemplary.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Exemplary exemplary) {
        String sql = "DELETE FROM exemplary WHERE image= ?;";  // Folosește id-ul exemplarlui
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, exemplary.getImage());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public Exemplary findById(int id) {
        String sql = "SELECT * FROM exemplary WHERE id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return getExemplaryFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Exemplary> findAll() {
        String sql = "SELECT * FROM exemplary;";
        List<Exemplary> exemplaries = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                exemplaries.add(getExemplaryFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exemplaries;
    }

    public List<Exemplary> findBySpecies(String species) {
        String sql = "SELECT e.* FROM exemplary e " +
                "JOIN plant p ON e.plant_id = p.id " +
                "WHERE p.species = ?;";
        List<Exemplary> exemplarys = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, species);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Exemplary exemplary = new Exemplary(
                        resultSet.getInt("id"),
                        resultSet.getInt("plant_id"),
                        resultSet.getString("zone"),
                        resultSet.getString("image")
                );
                exemplarys.add(exemplary);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exemplarys;
    }

    private Exemplary getExemplaryFromResultSet(ResultSet resultSet) throws SQLException {
        return new Exemplary(
                resultSet.getInt("id"),
                resultSet.getInt("plant_id"),
                resultSet.getString("zone"),
                resultSet.getString("image")
        );
    }
}