package pl.czakanski.thesis.common.dao;

import pl.czakanski.thesis.common.model.User;

public interface UserCustomDao {
    User findByEmail(String email);
}
