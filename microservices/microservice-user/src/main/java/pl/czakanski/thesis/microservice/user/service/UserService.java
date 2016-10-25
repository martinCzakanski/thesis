package pl.czakanski.thesis.microservice.user.service;

import pl.czakanski.thesis.common.model.User;
import pl.czakanski.thesis.common.request.UserDTO;

import java.util.List;

public interface UserService {

    User createUser(UserDTO userDTO);
    void updateUser(UserDTO userDTO);
    void disableUser(Integer id);
    void activeUser(Integer id);
    List<UserDTO> getAll();
    UserDTO get(Integer id);
    boolean existUser(Integer id);
    User getAuthenticatedUser(String email, String password);
}
