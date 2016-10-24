package pl.czakanski.thesis.common.converter;

import pl.czakanski.thesis.common.model.User;
import pl.czakanski.thesis.common.request.UserDTO;

public class UtilsDTO {

    public static User toUser(UserDTO userDTO) {
        User user = null;
        if(userDTO != null) {
            user = new User();
            user.setUserId(userDTO.getId());
            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());
            user.setPassword(userDTO.getPassword());
        }
        return user;
    }

    public static UserDTO toUserDTO(User user) {
        UserDTO userDTO = null;
        if(user != null) {
            userDTO = new UserDTO();
            userDTO.setId(user.getUserId());
            userDTO.setName(user.getName());
            userDTO.setEmail(user.getEmail());
            userDTO.setPassword(user.getPassword());
        }
        return userDTO;
    }
}
