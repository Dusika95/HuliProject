package huli.example.huliwebshop.controllers;

import huli.example.huliwebshop.models.Comment;
import huli.example.huliwebshop.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/comments")
public class CommentController {
    CommentService commentService;
    @Autowired
    public CommentController(CommentService commentService){
        this.commentService= commentService;
    }
}
