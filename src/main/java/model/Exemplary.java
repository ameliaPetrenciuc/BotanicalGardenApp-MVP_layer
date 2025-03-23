package model;

public class Exemplary {
    private int id;
    private int plantId;
    private String zone;
    private String image;

    public Exemplary(int id, int plantId, String zone, String image) {
        this.id = id;
        this.plantId = plantId;
        this.zone = zone;
        this.image = image;
    }

    public Exemplary(int plantId, String zone, String image) {
        this.plantId = plantId;
        this.zone = zone;
        this.image = image;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPlantId() { return plantId; }
    public void setPlantId(int plantId) { this.plantId = plantId; }

    public String getZone() { return zone; }
    public void setZone(String zone) { this.zone = zone; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    @Override
    public String toString() {
        return "Exemplar{" +
                "id=" + id +
                ", plantId=" + plantId +
                ", zone='" + zone + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
