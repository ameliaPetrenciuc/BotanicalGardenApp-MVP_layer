package view.model;
import javafx.beans.property.*;

public class PlantDTO {
    private StringProperty name;
    private StringProperty species;
    private StringProperty type;
    private BooleanProperty carnivorous;
    private StringProperty image;

    public void setName(String name){
        nameProperty().set(name);
    }

    public String getName(){
        return nameProperty().get();
    }

    public StringProperty nameProperty(){
        if (name==null){
            name=new SimpleStringProperty(this, "name");
        }
        return name;
    }

    public void setSpecies(String species){
        speciesProperty().set(species);
    }

    public String getSpecies(){
        return speciesProperty().get();
    }

    public StringProperty speciesProperty(){
        if (species==null){
            species=new SimpleStringProperty(this, "species");
        }
        return species;
    }

    public void setType(String type){
        typeProperty().set(type);
    }

    public String getType(){
        return typeProperty().get();
    }

    public StringProperty typeProperty(){
        if (type==null){
            type=new SimpleStringProperty(this, "type");
        }
        return type;
    }

    public void setCarnivorous(Boolean carnivorous){
        carnivorousProperty().set(carnivorous);
    }

    public Boolean isCarnivorous(){
        return carnivorousProperty().get();
    }

    public BooleanProperty carnivorousProperty(){
        if (carnivorous==null){
            carnivorous=new SimpleBooleanProperty(this, "carnivorous");
        }
        return carnivorous;
    }


    public void setImage(String image){
        imgProperty().set(image);
    }

    public String getImage(){
        return imgProperty().get();
    }

    public StringProperty imgProperty(){
        if (image==null){
            image=new SimpleStringProperty(this, "image");
        }
        return image;
    }

    @Override
    public String toString() {
        return "PlantDTO{" +
                "name='" + getName() + '\'' +
                ", species='" + getSpecies() + '\'' +
                ", type='" + getType() + '\'' +
                ", carnivorous=" + isCarnivorous() +
                ", image='" + getImage() + '\'' +
                '}';
    }

}
