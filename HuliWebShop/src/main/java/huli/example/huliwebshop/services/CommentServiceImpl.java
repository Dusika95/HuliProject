package huli.example.huliwebshop.services;

import huli.example.huliwebshop.repository.ICommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    private ICommentRepository iCommentRepository;

    @Autowired
    public CommentServiceImpl(ICommentRepository iCommentRepository) {
        this.iCommentRepository = iCommentRepository;
    }
}
