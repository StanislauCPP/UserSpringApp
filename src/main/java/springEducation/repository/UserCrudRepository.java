package springEducation.repository;

import springEducation.entity.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCrudRepository extends CrudRepository<User, Long> {}
