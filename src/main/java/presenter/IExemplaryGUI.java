package presenter;

import view.model.ExemplaryDTO;
import java.util.List;

public interface IExemplaryGUI {
    void updateExemplaryList(Integer plant_id, String zone, String image);
    void updateExemplaryInTable(Integer id, Integer plant_id, String zone, String image) ;
    Integer getPlantId();
    String getZone();
    String getImage();
    void setImage(String image);
    String getSpecies();
    void removeSelectedExemplaryFromTable();
    void clearExemplaryList();
    void displaySearchResults(List<ExemplaryDTO> exemplaryDTOs);
    Integer getSelectedId();
    Integer getSelectedPlantId();
    String getSelectedZone();
    String getSelectedImage();
    void showMessage(String title, String message);
}
