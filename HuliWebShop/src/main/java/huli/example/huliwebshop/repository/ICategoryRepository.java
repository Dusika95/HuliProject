package huli.example.huliwebshop.repository;

import huli.example.huliwebshop.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {
    @Transactional
    Category findByName(String categoryName);
}
