package recipes.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.entity.User;
@Repository
public interface UserRepo extends CrudRepository<User,String> {
    boolean existsByEmail(String email);
    User findUserByEmail(String email);
}
