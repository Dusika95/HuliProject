package huli.example.huliwebshop.DTOs;

public class CommentCreateDTO {
    String comment;
    Long productId;
    Long userId;

    public CommentCreateDTO() {
    }

    public CommentCreateDTO(String comment, Long productId, Long userId) {
        this.comment = comment;
        this.productId = productId;
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
