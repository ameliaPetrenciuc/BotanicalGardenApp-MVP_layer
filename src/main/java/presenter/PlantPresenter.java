package presenter;

import model.Exemplary;
import model.Plant;
import model.repository.exemplary.ExemplaryRepositoryMySQL;
import model.repository.plant.PlantRepositoryMySQL;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import view.ExemplaryView;
import view.model.ExemplaryDTO;
import view.model.ExemplaryDTOBuilder;
import view.model.PlantDTO;
import view.model.PlantDTOBuilder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.io.FileWriter;

public class PlantPresenter {
    private IPlantGUI iPlantGUI;
    private PlantRepositoryMySQL plantRepository;
    private ExemplaryRepositoryMySQL exemplaryRepository;
;

    public PlantPresenter(IPlantGUI iPlantGUI) {
        this.iPlantGUI = iPlantGUI;
        this.plantRepository = new PlantRepositoryMySQL();
        this.exemplaryRepository = new ExemplaryRepositoryMySQL();
    }

    public void addPlant() {
        String name = iPlantGUI.getPlantName();
        String species = iPlantGUI.getSpecies();
        String type = iPlantGUI.getPlantType();
        boolean carnivorous = Boolean.parseBoolean(iPlantGUI.isCarnivorous());
        String image = iPlantGUI.getImage();

        if (!validatePlantData(name, species, type, image)) {
            iPlantGUI.showMessage("Error", "Failed to add plant.");
        }

        Plant plant = new Plant(name, species, type, carnivorous, image);
        boolean ok = plantRepository.add(plant);
        if (ok) {
            iPlantGUI.updatePlantList(name, species, type, carnivorous, image);
            iPlantGUI.showMessage("Success", "Plant added successfully!");
        } else {
            iPlantGUI.showMessage("Error", "Failed to add plant.");        }
    }

    public void deletePlant() {
        String name = iPlantGUI.getSelectedPlantName();
        String species = iPlantGUI.getSelectedPlantSpecies();
        String type = iPlantGUI.getSelectedPlantType();
        boolean carnivorous = iPlantGUI.getSelectedPlantCarnivorous();
        String image = iPlantGUI.getSelectedPlantImage();

        if (!validatePlantData(name, species, type, image)) {
            iPlantGUI.showMessage("Error", "Failed to delete plant.");
        }

        Plant plant = new Plant(name, species, type, carnivorous, image);

        boolean ok = plantRepository.delete(plant);
        if (ok) {
            iPlantGUI.removeSelectedPlantFromTable();
            iPlantGUI.showMessage("Success", "Plant deleted successfully!");
        } else {
            iPlantGUI.showMessage("Error", "Failed to delete plant.");
        }
    }

    public void updatePlant(){
        String name = iPlantGUI.getPlantName();
        String species = iPlantGUI.getSpecies();
        String type = iPlantGUI.getPlantType();
        boolean carnivorous = Boolean.parseBoolean(iPlantGUI.isCarnivorous());
        String image = iPlantGUI.getImage();

        if (!validatePlantData(name, species, type, image)) {
            iPlantGUI.showMessage("Error", "Failed to update plant.");
        }

        Plant plant = new Plant(name, species, type, carnivorous, image);
        boolean ok = plantRepository.update(plant,name,species,type,carnivorous,image);
        if (ok) {
            iPlantGUI.updatePlantInTable(name, species, type, carnivorous, image);
            iPlantGUI.showMessage("Success", "Plant updated successfully!");
        } else {
            iPlantGUI.showMessage("Error", "Failed to update plant.");
        }
    }

    public void sortPlants(String criteria) {
        List<PlantDTO> sortedPlants = new ArrayList<>(iPlantGUI.getAllPlants());

        switch (criteria) {
            case "Type":
                sortedPlants.sort(Comparator.comparing(PlantDTO::getType).thenComparing(PlantDTO::getSpecies));
                break;
            case "Species":
                sortedPlants.sort(Comparator.comparing(PlantDTO::getSpecies).thenComparing(PlantDTO::getType));
                break;
            default:
                break;
        }
        iPlantGUI.clearPlantList();
        for (PlantDTO plantDTO : sortedPlants) {
            iPlantGUI.updatePlantList(
                    plantDTO.getName(),
                    plantDTO.getSpecies(),
                    plantDTO.getType(),
                    plantDTO.isCarnivorous(),
                    plantDTO.getImage()
            );
        }
    }

    public void filterPlants(String filterBy, String search) {
        List<Plant> filteredPlants = plantRepository.plantListFiltered(filterBy, search);

        List<PlantDTO> filteredPlantDTOs = new ArrayList<>();
        for (Plant plant : filteredPlants) {
            PlantDTO plantDTO = new PlantDTOBuilder()
                    .setName(plant.getName())
                    .setSpecies(plant.getSpecies())
                    .setType(plant.getType())
                    .setCarnivorous(plant.isCarnivorous())
                    .setImage(plant.getImage())
                    .build();
            filteredPlantDTOs.add(plantDTO);
        }
        iPlantGUI.clearPlantList();
        for (PlantDTO dto : filteredPlantDTOs) {
            iPlantGUI.updatePlantList(
                    dto.getName(),
                    dto.getSpecies(),
                    dto.getType(),
                    dto.isCarnivorous(),
                    dto.getImage()
            );
        }
    }

    public void savePlantsToCSV() {
        List<PlantDTO> plants = getAllPlants();
        System.out.println(plants);
        String filePath = "D:/Petrenciuc Amelia/Documents/PS/BotanicGarden_MVP/csv/plant.csv";

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("Name,Species,Type,Carnivorous,Image\n");
            for (PlantDTO plant : plants) {
                writer.write(String.format("%s,%s,%s,%s,%s\n",
                        plant.getName(),
                        plant.getSpecies(),
                        plant.getType(),
                        plant.isCarnivorous(),
                        plant.getImage()));
            }
            iPlantGUI.showMessage("Success", "Plants CSV file saved successfully!");
        } catch (IOException e) {
            iPlantGUI.showMessage("Error", "Failed to save CSV: " + e.getMessage());
        }
    }

    public void savePlantsToDOC() {
        List<PlantDTO> plants = getAllPlants();
        String filePath = "D:/Petrenciuc Amelia/Documents/PS/BotanicGarden_MVP/doc/plant.docx";

        try (XWPFDocument document = new XWPFDocument(); FileOutputStream out = new FileOutputStream(filePath)) {
            XWPFParagraph title = document.createParagraph();
            XWPFRun titleRun = title.createRun();
            titleRun.setText("Plant List");
            titleRun.setBold(true);
            titleRun.setFontSize(16);

            for (PlantDTO plant : plants) {
                XWPFParagraph paragraph = document.createParagraph();
                XWPFRun run = paragraph.createRun();
                run.setText(String.format("Name: %s, Species: %s, Type: %s, Carnivorous: %s, Image: %s",
                        plant.getName(),
                        plant.getSpecies(),
                        plant.getType(),
                        plant.isCarnivorous(),
                        plant.getImage()));
            }

            document.write(out);
            iPlantGUI.showMessage("Success", "Plants DOC file saved successfully!");
        } catch (IOException e) {
            iPlantGUI.showMessage("Error", "Failed to save DOC: " + e.getMessage());
        }
    }

    public void refresh() {

        loadPlants();
    }

    public List<PlantDTO> getAllPlants() {
        List<Plant> plants = plantRepository.findAll();
        List<PlantDTO> plantDTOs = new ArrayList<>();

        for (Plant plant : plants) {
            PlantDTO plantDTO = new PlantDTOBuilder()
                    .setName(plant.getName())
                    .setSpecies(plant.getSpecies())
                    .setType(plant.getType())
                    .setCarnivorous(plant.isCarnivorous())
                    .setImage(plant.getImage())
                    .build();
            plantDTOs.add(plantDTO);
        }
        return plantDTOs;
    }

    public void loadPlants() {
        List<PlantDTO> plantDTOs = getAllPlants();
        iPlantGUI.clearPlantList();
        for (PlantDTO dto : plantDTOs) {
            iPlantGUI.updatePlantList(dto.getName(), dto.getSpecies(), dto.getType(), dto.isCarnivorous(), dto.getImage());
        }
    }

    public void navigateToExemplaryView() {
        List<Exemplary> exemplaryEntities = exemplaryRepository.findAll();
        List<ExemplaryDTO> exemplaryDTOs = new ArrayList<>();

        for (Exemplary exemplary : exemplaryEntities) {
            ExemplaryDTO exemplaryDTO = new ExemplaryDTOBuilder()
                    .setId(exemplary.getId())
                    .setPlantId(exemplary.getPlantId())
                    .setZone(exemplary.getZone())
                    .setImage(exemplary.getImage())
                    .build();
            exemplaryDTOs.add(exemplaryDTO);
        }

        new ExemplaryView(exemplaryDTOs);
    }


    private boolean validatePlantData(String name, String species, String type, String image) {
        if (name == null || name.isEmpty()) {
            iPlantGUI.showMessage("Invalid Input", "Name cannot be empty.");
            return false;
        }
        if (species == null || species.isEmpty()) {
            iPlantGUI.showMessage("Invalid Input", "Species cannot be empty.");
            return false;
        }
        if (type == null || type.isEmpty()) {
            iPlantGUI.showMessage("Invalid Input", "Type cannot be empty.");
            return false;
        }
        if (image == null || image.isEmpty()) {
            iPlantGUI.showMessage("Invalid Input", "Image cannot be empty.");
            return false;
        }
        return true;
    }


}




