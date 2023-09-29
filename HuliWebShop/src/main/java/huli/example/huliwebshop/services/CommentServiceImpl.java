package huli.example.huliwebshop.services;


import huli.example.huliwebshop.DTOs.CommentCreateDTO;
import huli.example.huliwebshop.models.Comment;
import huli.example.huliwebshop.models.Product;
import huli.example.huliwebshop.models.User;
import huli.example.huliwebshop.repository.ICommentRepository;
import huli.example.huliwebshop.repository.IProductRepository;
import huli.example.huliwebshop.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private ICommentRepository iCommentRepository;
    private IProductRepository iProductRepository;
    private IUserRepository iUserRepository;

    @Autowired
    public CommentServiceImpl(ICommentRepository iCommentRepository, IProductRepository iProductRepository,IUserRepository iUserRepository) {
        this.iCommentRepository = iCommentRepository;
        this.iProductRepository = iProductRepository;
        this.iUserRepository= iUserRepository;
    }

    @Override
    public Comment createNewComment(CommentCreateDTO commentCreateDTO) throws Exception {
        Comment comment = new Comment();
        Product product = productValidator(commentCreateDTO.getProductId());
        User user = userValidator(commentCreateDTO.getUserId());

        comment.setComment(commentCreateDTO.getComment());
        comment.setProduct(product);
        comment.setUser(user);

        iCommentRepository.save(comment);
        return comment;
    }
    @Override
    public Comment deleteCommentById(Long id) throws Exception {
        Comment comment = iCommentRepository.findById(id).get();
        if (!iProductRepository.findById(id).isPresent()) {
            throw new Exception("that id does not exist");
        } else {
            iCommentRepository.deleteById(id);
            return comment;
        }
    }

    public Product productValidator(Long id) throws Exception {
        Optional<Product> optionalProduct = iProductRepository.findById(id);
        if (!optionalProduct.isPresent()) {
            throw new Exception("This product does not exist.");
        }
        return optionalProduct.get();
    }
    public User userValidator(Long id) throws Exception {
        Optional<User> optionalUser = iUserRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new Exception("This user does not exist.");
        }
        return optionalUser.get();
    }
}
