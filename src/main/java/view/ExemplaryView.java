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
import presenter.ExemplaryPresenter;
import presenter.IExemplaryGUI;
import view.model.ExemplaryDTO;
import view.model.ExemplaryDTOBuilder;
import view.model.PlantDTO;

import java.util.List;
import java.util.Objects;

public class ExemplaryView implements IExemplaryGUI {
    private TableView<ExemplaryDTO> exemplaryTableView;
    private final ObservableList<ExemplaryDTO> exemplaryObservableList;
    private TextField plantIdTextField;
    private TextField zoneTextField;
    private TextField imgTextField;
    private TextField searchTextField;


    private Label plantIdLabel;
    private Label zoneLabel;
    private Label imgLabel;
    private Label searchLabel;

    private Button saveButton;
    private Button deleteButton;
    private Button updateButton;
    private Button searchButton;
    private Button refreshButton;

    private ExemplaryPresenter presenter;
    private Stage primaryStage;

    public ExemplaryView(List<ExemplaryDTO> exemplaryDTO) {
        primaryStage = new Stage(); //
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Botanic Garden - Exemplary View");
        this.presenter = new ExemplaryPresenter(this);
        GridPane gridPane = new GridPane();
        initializedGridPage(gridPane);

        Scene scene = new Scene(gridPane, 820, 580);
        primaryStage.setScene(scene);

        exemplaryObservableList = FXCollections.observableArrayList(exemplaryDTO);
        initTableView(gridPane);
        initSaveOptions(gridPane);

        primaryStage.show();
    }

    private void initializedGridPage(GridPane gridPane){
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.setPadding(new Insets(25,25,25,25));
    }

    private void initTableView(GridPane gridPane){
        exemplaryTableView=new TableView<ExemplaryDTO>();

        exemplaryTableView.setPlaceholder(new Label("No Exemplarys to display"));

        TableColumn<ExemplaryDTO, Number> idColumn = new TableColumn<>("id");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setVisible(false);
        TableColumn<ExemplaryDTO, Integer> plantIdColumn = new TableColumn<>("Plant_id");
        plantIdColumn.setCellValueFactory(new PropertyValueFactory<>("plantId"));

        TableColumn<ExemplaryDTO, String> zoneColumn = new TableColumn<>("Zone");
        zoneColumn.setCellValueFactory(new PropertyValueFactory<>("zone"));

        TableColumn<ExemplaryDTO, String> imageColumn = new TableColumn<>("Image");
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));

        imageColumn.setCellFactory(column -> new TableCell<ExemplaryDTO, String>() {
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

        exemplaryTableView.getColumns().addAll(idColumn,plantIdColumn,zoneColumn,imageColumn);
        exemplaryTableView.setItems(exemplaryObservableList);

        gridPane.add(exemplaryTableView,0,0,5,1);

    }

    private void initSaveOptions(GridPane gridPane) {

        plantIdLabel = new Label("Plant_id");
        gridPane.add(plantIdLabel, 1, 1);
        plantIdTextField = new TextField();
        gridPane.add(plantIdTextField, 2, 1);

        zoneLabel = new Label("Zone");
        gridPane.add(zoneLabel, 3, 1);
        zoneTextField = new TextField();
        gridPane.add(zoneTextField, 4, 1);

        imgLabel = new Label("Image");
        gridPane.add(imgLabel, 5, 1);
        imgTextField = new TextField();
        gridPane.add(imgTextField, 6, 1);

        saveButton = new Button("Save");
        gridPane.add(saveButton, 5, 2);

        deleteButton = new Button("Delete");
        gridPane.add(deleteButton, 6, 2);

        updateButton = new Button("Update");
        gridPane.add(updateButton, 7, 2);

        searchLabel = new Label("Search species");
        gridPane.add(searchLabel, 1, 2);
        searchTextField = new TextField();
        gridPane.add(searchTextField, 2, 2);
        searchButton = new Button("Search");
        gridPane.add(searchButton, 3, 2);

        refreshButton = new Button("Refresh");
        gridPane.add(refreshButton, 2, 6);

        saveButton.setOnAction(event -> {
            presenter.addExemplary();
        });

        deleteButton.setOnAction(event->{
            //ExemplaryDTO selectedExemplary = exemplaryTableView.getSelectionModel().getSelectedItem();
            presenter.deleteExemplary();
        });

        updateButton.setOnAction(event -> {
            //ExemplaryDTO selectedExemplary = exemplaryTableView.getSelectionModel().getSelectedItem();
            presenter.updateExemplary();
        });

        searchButton.setOnAction(event->{
            String species = searchTextField.getText();
            presenter.searchExemplaryBySpecies(species);
        });

    }

    @Override
    public void updateExemplaryList(Integer plant_id, String zone, String image) {
        ExemplaryDTO exemplaryDTO = new ExemplaryDTOBuilder()
                .setPlantId(plant_id)
                .setZone(zone)
                .setImage(image)
                .build();
        exemplaryObservableList.add(exemplaryDTO);
        exemplaryTableView.refresh();
    }

    @Override
    public void updateExemplaryInTable(Integer id, Integer plant_id, String zone, String image) {
        for (ExemplaryDTO exemplaryDTO : exemplaryObservableList) {
            if (Objects.equals(exemplaryDTO.getId(), id)) {
                exemplaryDTO.setZone(zone);
                exemplaryDTO.setPlantId(plant_id);
                exemplaryDTO.setImage(image);
                exemplaryTableView.refresh();
                break;
            }
        }
    }

    @Override
    public void displaySearchResults(List<ExemplaryDTO> exemplaryDTOs) {
        exemplaryObservableList.clear();
        exemplaryObservableList.addAll(exemplaryDTOs);

        exemplaryTableView.refresh();
    }

    @Override
    public Integer getPlantId() {
        return Integer.valueOf(plantIdTextField.getText());
    }

    @Override
    public String getZone() {
        return zoneTextField.getText();
    }

    @Override
    public String getImage() {
        return imgTextField.getText();
    }

    @Override
    public void setImage(String image) {
    }

    @Override
    public String getSpecies() {
        return searchTextField.getText();
    }

    public Integer getSelectedId() {
        ExemplaryDTO selectedExemplary = exemplaryTableView.getSelectionModel().getSelectedItem();
        if (selectedExemplary != null) {
            return selectedExemplary.getId();
        }
        return -1;
    }

    @Override
    public Integer getSelectedPlantId() {
        ExemplaryDTO selectedExemplary = exemplaryTableView.getSelectionModel().getSelectedItem();
        if (selectedExemplary != null) {
            return selectedExemplary.getPlantId();
        }
        return -1;
    }

    @Override
    public String getSelectedZone() {
        ExemplaryDTO selectedExemplary = exemplaryTableView.getSelectionModel().getSelectedItem();
        if (selectedExemplary != null) {
            return selectedExemplary.getZone();
        }
        return null;
    }

    @Override
    public String getSelectedImage() {
        ExemplaryDTO selectedExemplary = exemplaryTableView.getSelectionModel().getSelectedItem();
        if (selectedExemplary != null) {
            return selectedExemplary.getImage();
        }
        return null;
    }

    @Override
    public void removeSelectedExemplaryFromTable() {
        ExemplaryDTO selectedExemplary = exemplaryTableView.getSelectionModel().getSelectedItem();
        if (selectedExemplary != null) {
            exemplaryObservableList.remove(selectedExemplary); // Eliminăm planta din ObservableList
            exemplaryTableView.refresh(); // Actualizăm tabelul
        }
    }

    @Override
    public void clearExemplaryList() {

    }

    @Override
    public void showMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
