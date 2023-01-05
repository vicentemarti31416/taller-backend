package vicente.marti.microserviciousers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vicente.marti.microserviciousers.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
