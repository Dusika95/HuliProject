package huli.example.huliwebshop.DTOs;

public class ProductCreateDTO {
    private String name;
    private String description;
    private String picture;
    private String category;
    public ProductCreateDTO(){

    }
    public ProductCreateDTO(String name, String description, String picture, String category) {
        this.name = name;
        this.description = description;
        this.picture = picture;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
