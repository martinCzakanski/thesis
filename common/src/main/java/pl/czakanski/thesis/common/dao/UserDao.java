package pl.czakanski.thesis.common.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.czakanski.thesis.common.model.User;

public interface UserDao extends CrudRepository<User, Integer> {

    @Query("select u from User u where u.active = 1 and u.email=:email and u.password=:password")
    User getAuthenticatedUser(@Param("email") String email, @Param("password") String password);
}
