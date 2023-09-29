package huli.example.huliwebshop.DTOs;


public class ProductUpdateDTO {
    private String name;
    private String description;
    private String picture;
    private int price;
    private int quantity;
    private String category;

    public ProductUpdateDTO() {
    }

    public ProductUpdateDTO(String name, String description, String picture, int price, int quantity, String category) {
        this.name = name;
        this.description = description;
        this.picture = picture;
        this.price = price;
        this.quantity = quantity;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
