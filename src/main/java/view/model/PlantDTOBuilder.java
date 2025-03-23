package view.model;

public class PlantDTOBuilder {
    private PlantDTO plantDTO;
    public PlantDTOBuilder(){
        plantDTO=new PlantDTO();
    }

    public PlantDTOBuilder setName(String name){
        plantDTO.setName(name);
        return this;
    }

    public PlantDTOBuilder setSpecies(String species){
        plantDTO.setSpecies(species);
        return this;
    }

    public PlantDTOBuilder setType(String type){
        plantDTO.setType(type);
        return this;
    }

    public PlantDTOBuilder setCarnivorous(Boolean carnivorous){
        plantDTO.setCarnivorous(carnivorous);
        return this;
    }

    public PlantDTOBuilder setImage(String image){
        plantDTO.setImage(image);
        return this;
    }

    public PlantDTO build(){
        return plantDTO;
    }
}
