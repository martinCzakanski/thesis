package pl.czakanski.thesis.client.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.czakanski.thesis.client.server.service.UserService;
import pl.czakanski.thesis.common.converter.UtilsDTO;
import pl.czakanski.thesis.common.dao.UserDao;
import pl.czakanski.thesis.common.model.User;
import pl.czakanski.thesis.common.request.UserDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public UserDTO get(Integer id) {
        return UtilsDTO.toUserDTO(userDao.findOne(id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User createUser(UserDTO userDTO) {
        User user = UtilsDTO.toUser(userDTO);
        if(user != null) {
            user.setCreatedDate(new Date());
            user.setActive(Boolean.FALSE);
            user.setSendNotification(Boolean.TRUE);
            user = userDao.save(user);
        }
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(UserDTO userDTO) {
        User user = userDao.findOne(userDTO.getId());
        if(user != null) {
            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());
            user.setPassword(userDTO.getPassword());
            userDao.save(user);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void disableUser(Integer id) {
        User user = userDao.findOne(id);
        if(user != null) {
            user.setActive(Boolean.FALSE);
            userDao.save(user);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void activeUser(Integer id) {
        User user = userDao.findOne(id);
        if(user != null) {
            user.setActive(Boolean.TRUE);
            userDao.save(user);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<UserDTO> getAll() {
        List<UserDTO> users = new ArrayList<UserDTO>();
        for(User user : userDao.findAll()) {
            users.add(UtilsDTO.toUserDTO(user));
        }
        return users;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public boolean existUser(Integer id) {
        return userDao.exists(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public User getAuthenticatedUser(String email, String password) {
        return userDao.getAuthenticatedUser(email, password);
    }
}