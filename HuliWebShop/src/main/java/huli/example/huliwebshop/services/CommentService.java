package huli.example.huliwebshop.services;

import huli.example.huliwebshop.DTOs.CommentCreateDTO;
import huli.example.huliwebshop.models.Comment;

public interface CommentService {
    String createNewComment(CommentCreateDTO commentCreateDTO) throws Exception;

    String deleteCommentById(Long id) throws Exception;
}
