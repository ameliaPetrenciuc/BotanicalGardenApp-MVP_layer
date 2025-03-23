package presenter;

import view.model.PlantDTO;

import java.util.List;

public interface IPlantGUI {
    void updatePlantList(String name, String species, String type, boolean carnivorous, String image);
    void updatePlantInTable(String name, String species, String type, Boolean carnivorous, String image) ;
    String getPlantName();
    String getSpecies();
    void setSpecies(String species);
    String getPlantType();
    String isCarnivorous();
    String getImage();
    void setImage(String image);
    String getSelectedPlantName();
    String getSelectedPlantSpecies();
    String getSelectedPlantType();
    boolean getSelectedPlantCarnivorous();
    String getSelectedPlantImage();
    void removeSelectedPlantFromTable();
    List<PlantDTO>  getAllPlants();
    void clearPlantList();
    void showMessage(String title, String message);
}
