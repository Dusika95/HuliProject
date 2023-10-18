package huli.example.huliwebshop.services;

import huli.example.huliwebshop.DTOs.RatingCreateDTO;
import huli.example.huliwebshop.models.Rating;

public interface RatingService {
    String createRating(RatingCreateDTO ratingCreateDTO) throws Exception;
}
