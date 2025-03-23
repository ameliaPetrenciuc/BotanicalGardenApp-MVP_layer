package model.repository.plant;

import model.Plant;

import java.util.List;

public interface PlantRepository {
    boolean add(Plant plant);
    boolean update(Plant plant, String name, String species, String type, Boolean carnivorous, String image);
    boolean delete(Plant plant);
    Plant findById(int id);
    List<Plant> findAll();
    List<Plant> plantListFiltered(String filterBy, String search);
}
