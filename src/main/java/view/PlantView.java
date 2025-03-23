package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import presenter.IPlantGUI;
import presenter.PlantPresenter;
import view.model.PlantDTO;
import view.model.PlantDTOBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlantView implements IPlantGUI {
    private TableView<PlantDTO> plantTableView;
    private final ObservableList<PlantDTO> plantObservableList;
    private TextField nameTextField;
    private TextField speciesTextField;
    private TextField typeTextField;
    private TextField carnivorousTextField;
    private TextField imgTextField;
    private TextField searchTextField;

    private Label nameLabel;
    private Label speciesLabel;
    private Label typeLabel;
    private Label carnivorousLabel;
    private Label imgLabel;
    private Button saveButton;
    private Button deleteButton;
    private Button updateButton;
    private Button filterButton;
    private Button refreshButton;
    private Button viewExemplaryButton;
    private Button csvButton;
    private Button docButton;

    private ComboBox<String> comboBox = new ComboBox<>();
    private ComboBox<String> comboBoxFilter = new ComboBox<>();

    private PlantPresenter presenter;

    public PlantView(Stage primaryStage, List<PlantDTO> plantDTO){
        primaryStage.setTitle("Botanic Garden");

        GridPane gridPane=new GridPane();
        initializedGridPage(gridPane);

        Scene scene=new Scene(gridPane, 1020,580);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());
        primaryStage.setScene(scene);

        plantObservableList= FXCollections.observableArrayList(plantDTO);
        initTableView(gridPane);
        initSaveOptions(gridPane);
        primaryStage.show();
    }

    private void initializedGridPage(GridPane gridPane){
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        //gridPane.setGridLinesVisible(true);
        gridPane.setPadding(new Insets(25,25,25,25));
    }

    private void initTableView(GridPane gridPane){
        plantTableView=new TableView<PlantDTO>();
        plantTableView.setStyle("-fx-background-color: #ffffff; -fx-table-cell-border-color: transparent;");

        plantTableView.setPlaceholder(new Label("No plants to display"));
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(gridPane);

        TableColumn<PlantDTO, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<PlantDTO, String> speciesColumn = new TableColumn<>("Species");
        speciesColumn.setCellValueFactory(new PropertyValueFactory<>("species"));

        TableColumn<PlantDTO, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        TableColumn<PlantDTO, String> carnivorousColumn = new TableColumn<>("Carnivorous");
        carnivorousColumn.setCellValueFactory(new PropertyValueFactory<>("carnivorous"));

        TableColumn<PlantDTO, String> imageColumn = new TableColumn<>("Image");
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));

        imageColumn.setCellFactory(column -> new TableCell<PlantDTO, String>() {
            @Override
            protected void updateItem(String imagePath, boolean empty) {
                super.updateItem(imagePath, empty);

                if (empty || imagePath == null || imagePath.isEmpty()) {
                    setText(null);
                    setTooltip(null);
                } else {
                    setText(imagePath);

                    Tooltip tooltip = new Tooltip();
                    ImageView imageView = new ImageView(new Image("file:"+imagePath));
                    imageView.setFitWidth(200);
                    imageView.setPreserveRatio(true);
                    tooltip.setGraphic(imageView);

                    setTooltip(tooltip);
                }
            }
        });
        plantTableView.getColumns().addAll(nameColumn,speciesColumn,typeColumn,carnivorousColumn,imageColumn);
        plantTableView.setItems(plantObservableList);

        gridPane.add(plantTableView,0,0,4,1);
    }

    private void initSaveOptions(GridPane gridPane){

        nameLabel=new Label("Name");
        gridPane.add(nameLabel,1,1);
        nameTextField=new TextField();
        gridPane.add(nameTextField,2,1);

        speciesLabel=new Label("Species");
        gridPane.add(speciesLabel,3,1);
        speciesTextField=new TextField();
        gridPane.add(speciesTextField,4,1);

        typeLabel=new Label("Type");
        gridPane.add(typeLabel,1,2);
        typeTextField=new TextField();
        gridPane.add(typeTextField,2,2);

        carnivorousLabel=new Label("Carnivorous");
        gridPane.add(carnivorousLabel,3,2);
        carnivorousTextField=new TextField();
        gridPane.add(carnivorousTextField,4,2);

        imgLabel=new Label("Image");
        gridPane.add(imgLabel,5,1);
        imgTextField=new TextField();
        gridPane.add(imgTextField,6,1);

        saveButton=new Button("Save");
        gridPane.add(saveButton,5,2);

        deleteButton=new Button("Delete");
        gridPane.add(deleteButton,6,2);

        updateButton=new Button("Update");
        gridPane.add(updateButton,7,2);

        filterButton=new Button("Filter");
        gridPane.add(filterButton,2,5);
        refreshButton=new Button("Refresh");
        gridPane.add(refreshButton,3,4);

        csvButton=new Button("CSV");
        gridPane.add(csvButton,3,3);
        docButton=new Button("DOC");
        gridPane.add(docButton,4,3);

        viewExemplaryButton=new Button("View Exemplary");
        gridPane.add(viewExemplaryButton,4,4);

        comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Species", "Type");
        comboBox.setValue("Sort By");
        gridPane.add(comboBox, 1, 3);

        comboBoxFilter = new ComboBox<>();
        comboBoxFilter.getItems().addAll("Species", "Type","Carnivorous");
        comboBoxFilter.setValue("Filter By");
        gridPane.add(comboBoxFilter, 2, 3);

        searchTextField=new TextField();
        gridPane.add(searchTextField,2,4);

        saveButton.setOnAction(event -> {
            presenter.addPlant();
        });

        deleteButton.setOnAction(event->{
            presenter.deletePlant();
        });

        updateButton.setOnAction(event -> {
            presenter.updatePlant();
        });

        comboBox.setOnAction(event -> {
            String selectedValue = comboBox.getSelectionModel().getSelectedItem();
            if (selectedValue != null) {
                presenter.sortPlants(selectedValue);
            }
        });

        filterButton.setOnAction((event->{
            String filterBy = comboBoxFilter.getSelectionModel().getSelectedItem();
            String search = searchTextField.getText();
            presenter.filterPlants(filterBy, search);
        }));

        refreshButton.setOnAction(event -> {
            presenter.refresh();
        });

        viewExemplaryButton.setOnAction(event -> {
            presenter.navigateToExemplaryView( );
        });

        csvButton.setOnAction(event -> {
            presenter.savePlantsToCSV();
        });

        docButton.setOnAction(event -> {
            presenter.savePlantsToDOC();
        });
    }

    @Override
    public void updatePlantList(String name, String species, String type, boolean carnivorous, String image) {
        PlantDTO plantDTO = new PlantDTOBuilder()
                .setName(name)
                .setSpecies(species)
                .setType(type)
                .setCarnivorous(carnivorous)
                .setImage(image)
                .build();
        plantObservableList.add(plantDTO);
        plantTableView.refresh();
    }

    @Override
    public void updatePlantInTable(String name, String species, String type, Boolean carnivorous, String image) {
        for (PlantDTO plantDTO : plantObservableList) {
            if (Objects.equals(plantDTO.getName(), name)) {
                plantDTO.setSpecies(species);
                plantDTO.setType(type);
                plantDTO.setCarnivorous(carnivorous);
                plantDTO.setImage(image);
                plantTableView.refresh();
                break;
            }
        }
    }

    public void setPresenter(PlantPresenter presenter) {
        this.presenter = presenter;
        presenter.loadPlants();
    }

    @Override
    public void showMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public String getPlantName() {
        return nameTextField.getText();
    }

    @Override
    public String getSpecies() {
        return speciesTextField.getText();
    }

    @Override
    public void setSpecies(String species) {

    }

    @Override
    public String getPlantType() {
        return typeTextField.getText();
    }

    @Override
    public String isCarnivorous() {
        return carnivorousTextField.getText();
    }

    @Override
    public String getImage() {
        return imgTextField.getText();
    }

    @Override
    public void setImage(String image) {

    }

    @Override
    public String getSelectedPlantName() {
        PlantDTO selectedPlant = plantTableView.getSelectionModel().getSelectedItem();
        return selectedPlant != null ? selectedPlant.getName() : null;
    }

    @Override
    public String getSelectedPlantSpecies() {
        PlantDTO selectedPlant = plantTableView.getSelectionModel().getSelectedItem();
        return selectedPlant != null ? selectedPlant.getSpecies() : null;
    }

    @Override
    public String getSelectedPlantType() {
        PlantDTO selectedPlant = plantTableView.getSelectionModel().getSelectedItem();
        return selectedPlant != null ? selectedPlant.getType() : null;
    }

    @Override
    public boolean getSelectedPlantCarnivorous() {
        PlantDTO selectedPlant = plantTableView.getSelectionModel().getSelectedItem();
        return selectedPlant != null ? selectedPlant.isCarnivorous() : false;
    }

    @Override
    public String getSelectedPlantImage() {
        PlantDTO selectedPlant = plantTableView.getSelectionModel().getSelectedItem();
        return selectedPlant != null ? selectedPlant.getImage() : null;
    }

    @Override
    public void removeSelectedPlantFromTable() {
        PlantDTO selectedPlant = plantTableView.getSelectionModel().getSelectedItem();
        if (selectedPlant != null) {
            plantObservableList.remove(selectedPlant);
            plantTableView.refresh();
        }
    }

    @Override
    public List<PlantDTO> getAllPlants() {
        return new ArrayList<>(plantObservableList);
    }

    @Override
    public void clearPlantList() {
        plantObservableList.clear();
    }
}
