package huli.example.huliwebshop.DTOs;

import java.util.List;

public class ProductGetByAloneDTO {
    private String name;
    private String picture;
    private String categoryName;
    private List<String> comment;
    private double star;
    public ProductGetByAloneDTO(){

    }
    public ProductGetByAloneDTO(String name, String picture, String categoryName, List<String> comment, double star) {
        this.name = name;
        this.picture = picture;
        this.categoryName = categoryName;
        this.comment = comment;
        this.star = star;
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

    public List<String> getComment() {
        return comment;
    }

    public void setComment(List<String> comment) {
        this.comment = comment;
    }

    public double getStar() {
        return star;
    }

    public void setStar(double star) {
        this.star = star;
    }
}
