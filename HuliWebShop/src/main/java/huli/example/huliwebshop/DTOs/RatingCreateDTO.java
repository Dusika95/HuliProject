package huli.example.huliwebshop.DTOs;

public class RatingCreateDTO {
    private int star;
    private Long productId;
    private Long userId;

    public RatingCreateDTO() {
    }

    public RatingCreateDTO(int star, Long productId, Long userId) {
        this.star = star;
        this.productId = productId;
        this.userId = userId;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
