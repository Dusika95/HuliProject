package huli.example.huliwebshop.DTOs;

public class ProductGetToListDTO {
    private Long id;
    private String name;
    private String picture;
    private String categoryName;
    private int price;
    private boolean available;

    public ProductGetToListDTO() {

    }

    public ProductGetToListDTO(Long id, String name, String picture, String categoryName, int price, boolean available) {
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.categoryName = categoryName;
        this.price = price;
        this.available = available;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
