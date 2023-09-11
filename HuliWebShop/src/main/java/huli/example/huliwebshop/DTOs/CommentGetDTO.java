package huli.example.huliwebshop.DTOs;

public class CommentGetDTO {
    private String comment;


    public CommentGetDTO() {

    }

    public CommentGetDTO(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


}
