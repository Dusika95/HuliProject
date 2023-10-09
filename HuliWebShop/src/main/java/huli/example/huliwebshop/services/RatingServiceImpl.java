package huli.example.huliwebshop.services;

import huli.example.huliwebshop.DTOs.RatingCreateDTO;
import huli.example.huliwebshop.models.Product;
import huli.example.huliwebshop.models.Rating;
import huli.example.huliwebshop.models.User;
import huli.example.huliwebshop.repository.IProductRepository;
import huli.example.huliwebshop.repository.IRatingRepository;
import huli.example.huliwebshop.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RatingServiceImpl implements RatingService {
    private IRatingRepository iRatingRepository;
    private IProductRepository iProductRepository;
    private IUserRepository iUserRepository;

    @Autowired
    public RatingServiceImpl(IRatingRepository iRatingRepository, IProductRepository iProductRepository, IUserRepository iUserRepository) {
        this.iRatingRepository = iRatingRepository;
        this.iProductRepository = iProductRepository;
        this.iUserRepository = iUserRepository;
    }

    @Override
    public String createRating(RatingCreateDTO ratingCreateDTO) throws Exception {
        Rating rating = new Rating();

        User user = userValidator(ratingCreateDTO.getUserId());

        Product product = productValidator(ratingCreateDTO.getProductId());

        if (ratingCreateDTO.getStar() < 1 || ratingCreateDTO.getStar() > 5) {
            throw new Exception("invalid rating value");
        }

        if (userAvailabilityChecker(ratingCreateDTO.getUserId(), ratingCreateDTO.getProductId())) {
            throw new Exception("user only can gives one time a rating by product");
        }

        rating.setStar(ratingCreateDTO.getStar());
        rating.setUser(user);
        rating.setProduct(product);
        iRatingRepository.save(rating);
        return "add ratings to "+rating.getProduct().getName();
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

    public boolean userAvailabilityChecker(Long userId, Long productId) {
        List<Rating> allRating = new ArrayList<>();
        allRating = iRatingRepository.findAll();
        for (int i = 0; i < allRating.size(); i++) {
            if (Objects.equals(allRating.get(i).getUser().getId(), userId) && Objects.equals(allRating.get(i).getProduct().getId(), productId)) {
                return true;
            }
        }
        return false;
    }
}
