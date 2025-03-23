package model.repository.plant;

import model.repository.DatabaseConnectionFactory;
import model.Plant;
import model.repository.JDBConnectionWrapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlantRepositoryMySQL implements PlantRepository {
    private final Connection connection;

    public PlantRepositoryMySQL() {
        this.connection = DatabaseConnectionFactory.getConnectionWrapper().getConnection();
    }

    @Override
    public boolean add(Plant plant) {
        String sql = "INSERT INTO plant (name, species, type, carnivorous, image) VALUES(?,?,?,?,?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, plant.getName());
            preparedStatement.setString(2, plant.getSpecies());
            preparedStatement.setString(3, plant.getType());
            preparedStatement.setBoolean(4, plant.isCarnivorous());
            preparedStatement.setString(5, plant.getImage());

            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Plant plant,String name, String species,String type, Boolean carnivorous, String image) {
        String sql = "UPDATE plant SET name = ?, species=?, type=?, carnivorous=?, image=? WHERE name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, species);
            preparedStatement.setString(3, type);
            preparedStatement.setBoolean(4, carnivorous);
            preparedStatement.setString(5, image);
            preparedStatement.setString(6, plant.getName());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Plant plant) {
        String sql = "DELETE FROM plant WHERE name = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, plant.getName());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Plant findById(int id) {
        String sql = "SELECT * FROM plant WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return getPlantFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Plant> findAll() {
        String sql = "SELECT * FROM plant;";
        List<Plant> plants = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                plants.add(getPlantFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plants;
    }

    public List<Plant> plantListFiltered(String filterBy, String search) {
        List<Plant> plants = new ArrayList<>();

        if (filterBy == null || filterBy.isEmpty() || search == null || search.isEmpty()) {
            return findAll();
        }

        String sql = null;
        PreparedStatement preparedStatement = null;

        try{
            switch (filterBy.toLowerCase()){
                case "species":
                    sql = "SELECT * FROM plant WHERE species = ?";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, search);
                    break;
                case "type":
                    sql = "SELECT * FROM plant WHERE type = ?";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, search);
                    break;
                case "carnivorous":
                    boolean isCarnivorous = search.equalsIgnoreCase("true") || Boolean.parseBoolean(search);
                    sql = "SELECT * FROM plant WHERE carnivorous = ?";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setBoolean(1, isCarnivorous);
                    break;
                default:
                    return findAll();
                }
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                plants.add(getPlantFromResultSet(resultSet));
            }
        resultSet.close();
        preparedStatement.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return plants;
    }

    private Plant getPlantFromResultSet(ResultSet resultSet) throws SQLException {
        return new Plant(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("species"),
                resultSet.getString("type"),
                resultSet.getBoolean("carnivorous"),
                resultSet.getString("image")
        );
    }
}
