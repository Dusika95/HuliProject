package huli.example.huliwebshop.repository;

import huli.example.huliwebshop.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRatingRepository extends JpaRepository<Rating, Long> {
}
