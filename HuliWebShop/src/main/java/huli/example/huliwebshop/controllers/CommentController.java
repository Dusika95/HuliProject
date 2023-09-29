package huli.example.huliwebshop.controllers;


import huli.example.huliwebshop.DTOs.CommentCreateDTO;
import huli.example.huliwebshop.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/comments")
public class CommentController {
    CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/create")
    public ResponseEntity createComment(@RequestBody CommentCreateDTO commentCreateDTO) {
        try {
            return ResponseEntity.ok().body(commentService.createNewComment(commentCreateDTO));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity deleteComment(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(commentService.deleteCommentById(id));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
