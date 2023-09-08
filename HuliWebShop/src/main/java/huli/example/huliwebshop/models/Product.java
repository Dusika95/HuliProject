package huli.example.huliwebshop.models;
import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String picture;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    //COMENT ONETOMANY
    public Product(){

    }
    public Product(Long id, String name, String description, String picture, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.picture = picture;
        this.category = category;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
