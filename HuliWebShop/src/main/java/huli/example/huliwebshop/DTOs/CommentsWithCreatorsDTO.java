package huli.example.huliwebshop.DTOs;

public class CommentsWithCreatorsDTO {
    private String comment;
    private String userName;
    private Long userId;
    public CommentsWithCreatorsDTO(){

    }

    public CommentsWithCreatorsDTO(String comment, String userName, Long userId) {
        this.comment = comment;
        this.userName = userName;
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
