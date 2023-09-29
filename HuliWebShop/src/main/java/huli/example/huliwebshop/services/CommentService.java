package huli.example.huliwebshop.services;

import huli.example.huliwebshop.DTOs.CommentCreateDTO;
import huli.example.huliwebshop.models.Comment;

public interface CommentService {
    Comment createNewComment(CommentCreateDTO commentCreateDTO) throws Exception;

    Comment deleteCommentById(Long id) throws Exception;
}
