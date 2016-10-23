package pl.czakanski.thesis.client.server.service;

import pl.czakanski.thesis.common.dto.UserDTO;
import pl.czakanski.thesis.common.model.User;

import java.util.List;

public interface UserService {

    User createUser(UserDTO userDTO);
    void updateUser(UserDTO userDTO);
    void disableUser(Integer id);
    void activeUser(Integer id);
    List<UserDTO> getAll();
    UserDTO get(Integer id);
    boolean existUser(Integer id);
}
