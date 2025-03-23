package view.model;

public class ExemplaryDTOBuilder {
    private ExemplaryDTO exemplaryDTO;

    public ExemplaryDTOBuilder() {
        exemplaryDTO = new ExemplaryDTO();
    }

    public ExemplaryDTOBuilder setId(int id) {
        exemplaryDTO.setId(id);
        return this;
    }

    public ExemplaryDTOBuilder setPlantId(int plantId) {
        exemplaryDTO.setPlantId(plantId);
        return this;
    }

    public ExemplaryDTOBuilder setZone(String zone) {
        exemplaryDTO.setZone(zone);
        return this;
    }

    public ExemplaryDTOBuilder setImage(String image) {
        exemplaryDTO.setImage(image);
        return this;
    }

    public ExemplaryDTO build() {
        return exemplaryDTO;
    }
}