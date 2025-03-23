package presenter;

import model.Exemplary;
import model.repository.exemplary.ExemplaryRepositoryMySQL;
import view.model.ExemplaryDTO;
import view.model.ExemplaryDTOBuilder;

import java.util.ArrayList;
import java.util.List;

public class ExemplaryPresenter {
    private IExemplaryGUI iExemplaryGUI;
    private ExemplaryRepositoryMySQL exemplaryRepository;

    public ExemplaryPresenter(IExemplaryGUI iExemplaryGUI) {
        this.iExemplaryGUI = iExemplaryGUI;
        this.exemplaryRepository = new ExemplaryRepositoryMySQL();
    }

    public void addExemplary() {
        Integer plantId = iExemplaryGUI.getPlantId();
        String zone = iExemplaryGUI.getZone();
        String image = iExemplaryGUI.getImage();

        if (!validateExemplaryData(plantId, zone, image)) {
            iExemplaryGUI.showMessage("Error", "Failed to update exemplary.");
        }

        Exemplary exemplary = new Exemplary(plantId, zone, image);

        boolean ok = exemplaryRepository.add(exemplary);
        if (ok) {
            iExemplaryGUI.showMessage("Success", "Exemplary added successfully!");
            iExemplaryGUI.updateExemplaryList(plantId, zone, image);
        } else {
            iExemplaryGUI.showMessage("Error", "Failed to added exemplary.");
        }
    }

    public void deleteExemplary() {
        int plantId = iExemplaryGUI.getSelectedPlantId();
        String zone = iExemplaryGUI.getSelectedZone();
        String image = iExemplaryGUI.getSelectedImage();

        if (!validateExemplaryData(plantId, zone, image)) {
            iExemplaryGUI.showMessage("Error", "Failed to delete exemplary.");
        }

        Exemplary exemplary = new Exemplary(plantId, zone, image);

        boolean ok = exemplaryRepository.delete(exemplary);
        if (ok) {
            iExemplaryGUI.showMessage("Success", "Exemplary deleted successfully!");
            iExemplaryGUI.removeSelectedExemplaryFromTable();
        } else {
            iExemplaryGUI.showMessage("Error", "Failed to delete exemplary.");
        }
    }

    public void updateExemplary() {
        Integer id = iExemplaryGUI.getSelectedId();
        Integer plantId = iExemplaryGUI.getPlantId();
        String zone = iExemplaryGUI.getZone();
        String image = iExemplaryGUI.getImage();

        if (!validateExemplaryData(plantId, zone, image)) {
            iExemplaryGUI.showMessage("Error", "Failed to update exemplary.");
        }

        Exemplary exemplary = new Exemplary(id,plantId, zone, image);

        boolean ok = exemplaryRepository.update(exemplary,plantId, zone, image);
        if (ok) {
            iExemplaryGUI.updateExemplaryInTable(id, plantId, zone, image);
            iExemplaryGUI.showMessage("Success", "Exemplary updated successfully!");
        } else {
            iExemplaryGUI.showMessage("Error", "Failed to update exemplary.");
        }
    }

    public void searchExemplaryBySpecies(String species) {
        if (!validateSpecies(species)) {
            iExemplaryGUI.showMessage("Error", "Faild to search!");
        }
        List<Exemplary> exemplaries = exemplaryRepository.findBySpecies(species);
        List<ExemplaryDTO> exemplaryDTOs = new ArrayList<>();
        if (exemplaries == null || exemplaries.isEmpty()) {
            iExemplaryGUI.showMessage("No Results", "No exemplaries found for the given species.");
            return;
        }

        for (Exemplary exemplary : exemplaries) {
            ExemplaryDTO exemplaryDTO = new ExemplaryDTOBuilder()
                    .setId(exemplary.getId())
                    .setPlantId(exemplary.getPlantId())
                    .setZone(exemplary.getZone())
                    .setImage(exemplary.getImage())
                    .build();
            exemplaryDTOs.add(exemplaryDTO);
        }
        iExemplaryGUI.displaySearchResults(exemplaryDTOs);
    }

    public void loadExemplarys() {
        List<ExemplaryDTO> exemplaryDTOs = getAllExemplarys();
        iExemplaryGUI.clearExemplaryList();
        for (ExemplaryDTO dto : exemplaryDTOs) {
            iExemplaryGUI.updateExemplaryList(dto.getPlantId(), dto.getZone(), dto.getImage());
        }
    }

    public void refresh() {
        loadExemplarys();
    }

    public List<ExemplaryDTO> getAllExemplarys() {
        List<Exemplary> exemplarys = exemplaryRepository.findAll();
        List<ExemplaryDTO> exemplarysDTOs = new ArrayList<>();
        System.out.println(exemplarys);
        for (Exemplary exemplary : exemplarys) {

            ExemplaryDTO exemplaryDTO = new ExemplaryDTOBuilder()
                    .setId(exemplary.getId())
                    .setPlantId(exemplary.getPlantId())
                    .setZone(exemplary.getZone())
                    .setImage(exemplary.getImage())
                    .build();
            exemplarysDTOs.add(exemplaryDTO);
        }
        return exemplarysDTOs;
    }

    private boolean validateExemplaryData(Integer plantId, String zone, String image) {
        if (plantId == null || plantId <= 0) {
            iExemplaryGUI.showMessage("Invalid Input", "Plant ID must be a positive number.");
            return false;
        }
        if (zone == null || zone.isEmpty()) {
            iExemplaryGUI.showMessage("Invalid Input", "Zone cannot be empty.");
            return false;
        }
        if (image == null || image.isEmpty()) {
            iExemplaryGUI.showMessage("Invalid Input", "Image cannot be empty.");
            return false;
        }
        return true;
    }

    private boolean validateSpecies(String species) {
        if (species == null || species.isEmpty()) {
            iExemplaryGUI.showMessage("Invalid Input", "Species cannot be empty.");
            return false;
        }
        return true;
    }
}
