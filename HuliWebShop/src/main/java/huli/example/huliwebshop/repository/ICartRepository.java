package huli.example.huliwebshop.repository;

import huli.example.huliwebshop.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ICartRepository extends JpaRepository<Cart, Long> {
  Cart findByUserId(Long userId);
  @Query("SELECT c FROM Cart c WHERE c.lastModified < :thresholdDate")
  List<Cart> findInactiveCarts(@Param("thresholdDate") Date thresholdDate);
}
