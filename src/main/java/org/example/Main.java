package org.example;

import javafx.application.Application;
import javafx.stage.Stage;
import presenter.PlantPresenter;
import view.PlantView;
import view.model.PlantDTO;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        List<PlantDTO> initialPlants = new ArrayList<>();
        PlantView plantView = new PlantView(primaryStage, initialPlants);
        PlantPresenter plantPresenter = new PlantPresenter(plantView);
        plantView.setPresenter(plantPresenter);


    }

    public static void main(String[] args) {
        launch(args);
    }
}