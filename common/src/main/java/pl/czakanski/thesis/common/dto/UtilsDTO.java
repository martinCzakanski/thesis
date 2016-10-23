package pl.czakanski.thesis.common.dto;

import pl.czakanski.thesis.common.model.User;

public class UtilsDTO {

    public static User toUser(UserDTO userDTO) {
        User user = null;
        if(userDTO != null) {
            user = new User();
            user.setId(userDTO.getId());
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
            userDTO.setId(user.getId());
            userDTO.setName(user.getName());
            userDTO.setEmail(user.getEmail());
            userDTO.setPassword(user.getPassword());
        }
        return userDTO;
    }
}
