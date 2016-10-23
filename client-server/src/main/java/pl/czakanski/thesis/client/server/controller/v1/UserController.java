package pl.czakanski.thesis.client.server.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.czakanski.thesis.client.server.service.NotificationService;
import pl.czakanski.thesis.client.server.service.UserService;
import pl.czakanski.thesis.common.dto.UserDTO;
import pl.czakanski.thesis.common.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    private NotificationService notificationService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody UserDTO user, HttpServletRequest request) {
        User createdUser = userService.createUser(user);
        notificationService.sentNotification(createdUser, request.getRequestURL().toString());
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UserDTO>> list() {
        return new ResponseEntity<List<UserDTO>>(userService.getAll(), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable("id") int userId, @RequestBody UserDTO user) {
        userService.updateUser(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("id") int userId) {
        userService.disableUser(userId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserDTO> get(@PathVariable("id") int userId) {
        return new ResponseEntity<UserDTO>(userService.get(userId), HttpStatus.OK);
    }
}
