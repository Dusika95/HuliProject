package huli.example.huliwebshop.controllers;

import huli.example.huliwebshop.DTOs.RatingCreateDTO;
import huli.example.huliwebshop.services.RatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/ratings")
public class RatingController {
    RatingService ratingService;
    public RatingController(RatingService ratingService){
        this.ratingService=ratingService;
    }

    @PostMapping("/create")
    public ResponseEntity createRating(@RequestBody RatingCreateDTO ratingCreateDTO){
        try{
            return ResponseEntity.ok().body(ratingService.createRating(ratingCreateDTO));
        } catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
