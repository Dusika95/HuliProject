package huli.example.huliwebshop.repository;

import huli.example.huliwebshop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
  User findByEmail(String email);
}
