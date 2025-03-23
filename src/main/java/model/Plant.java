package model;

public class Plant {
    private int id;
    private String name;
    private String species;
    private String type;
    private boolean carnivorous;
    private String image;

    public Plant(int id, String name, String species, String type, boolean carnivorous, String image) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.type = type;
        this.carnivorous = carnivorous;
        this.image = image;
    }

    public Plant(String name, String species, String type, boolean carnivorous, String image) {
        this.name = name;
        this.species = species;
        this.type = type;
        this.carnivorous = carnivorous;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isCarnivorous() {
        return carnivorous;
    }

    public void setCarnivorous(boolean carnivorous) {
        this.carnivorous = carnivorous;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Plant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", species=" + species +
                ", type=" + type +
                ", carnivorous=" + carnivorous +
                ", image='" + image + '\'' +
                '}';
    }
}
