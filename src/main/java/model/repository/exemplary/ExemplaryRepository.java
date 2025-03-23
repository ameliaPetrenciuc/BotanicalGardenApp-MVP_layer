package model.repository.exemplary;

import model.Exemplary;
import model.Plant;

import java.util.List;

public interface ExemplaryRepository<T> {
    boolean add(Exemplary exemplary);
    boolean update(Exemplary exemplary,Integer plant_id, String zone, String image);
    boolean delete(Exemplary exemplary);
    Exemplary findById(int id);
    List<Exemplary> findAll();
}
