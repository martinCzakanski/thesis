package pl.czakanski.thesis.common.dao;

import org.springframework.data.repository.CrudRepository;
import pl.czakanski.thesis.common.model.User;

public interface UserDao extends CrudRepository<User, Integer>, UserCustomDao {

}
