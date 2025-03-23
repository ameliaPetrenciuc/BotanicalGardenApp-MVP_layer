package view.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ExemplaryDTO {
    private IntegerProperty id;
    private IntegerProperty plant_id; // Referință către planta asociată
    private StringProperty zone;
    private StringProperty image;

    // Getters și setters pentru plantId
    public int getId() {
        return idProperty().get();
    }

    public void setId(int id) {
        idProperty().set(id);
    }

    public IntegerProperty idProperty() {
        if (id == null) {
            id = new SimpleIntegerProperty(this, "id");
        }
        return id;
    }

    public int getPlantId() {
        return plantIdProperty().get();
    }

    public void setPlantId(int plant_id) {
        plantIdProperty().set(plant_id);
    }

    public IntegerProperty plantIdProperty() {
        if (plant_id == null) {
            plant_id = new SimpleIntegerProperty(this, "plant_id");
        }
        return plant_id;
    }

    // Getters și setters pentru zone
    public String getZone() {
        return zoneProperty().get();
    }

    public void setZone(String zone) {
        zoneProperty().set(zone);
    }

    public StringProperty zoneProperty() {
        if (zone == null) {
            zone = new SimpleStringProperty(this, "zone");
        }
        return zone;
    }

    public String getImage() {
        return imageProperty().get();
    }

    public void setImage(String image) {
        imageProperty().set(image);
    }

    public StringProperty imageProperty() {
        if (image == null) {
            image = new SimpleStringProperty(this, "image");
        }
        return image;
    }

    @Override
    public String toString() {
        return "ExemplaryDTO{" +
                "plantId=" + getPlantId() +
                ", zone='" + getZone() + '\'' +
                ", image='" + getImage() + '\'' +
                '}';
    }
}