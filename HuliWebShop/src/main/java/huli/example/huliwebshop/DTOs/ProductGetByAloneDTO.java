package huli.example.huliwebshop.DTOs;

import java.util.List;

public class ProductGetByAloneDTO {
    private Long id;
    private String name;
    private String description;
    private String picture;
    private String categoryName;
    private List<CommentsWithCreatorsDTO> comment;
    private double star;
    private int price;
    private int quantity;
    public ProductGetByAloneDTO(){

    }

    public ProductGetByAloneDTO(Long id, String name, String description, String picture, String categoryName, List<CommentsWithCreatorsDTO> comment, double star, int price, int quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.picture = picture;
        this.categoryName = categoryName;
        this.comment = comment;
        this.star = star;
        this.price = price;
        this.quantity = quantity;
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

    public List<CommentsWithCreatorsDTO> getComment() {
        return comment;
    }

    public void setComment(List<CommentsWithCreatorsDTO> comment) {
        this.comment = comment;
    }

    public double getStar() {
        return star;
    }

    public void setStar(double star) {
        this.star = star;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
