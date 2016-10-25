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
import pl.czakanski.thesis.client.server.service.SessionService;
import pl.czakanski.thesis.client.server.service.UserService;
import pl.czakanski.thesis.common.helpers.MethodExecutor;
import pl.czakanski.thesis.common.model.User;
import pl.czakanski.thesis.common.request.ClientRequest;
import pl.czakanski.thesis.common.request.UserDTO;
import pl.czakanski.thesis.common.request.UserRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private SessionService sessionService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody final UserDTO request, final HttpServletRequest httpRequest) {
        User createdUser = userService.createUser(request);
        notificationService.sentNotification(createdUser, httpRequest.getRequestURL().toString());
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UserDTO>> list() {
        return new ResponseEntity<List<UserDTO>>(userService.getAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable("id") final int userId, @RequestBody final UserRequest request) {
        return new MethodExecutor(request) {
            @Override
            protected boolean isAuthenticated(ClientRequest request) {
                return sessionService.isAuthenticated(request);
            }

            @Override
            protected ResponseEntity execute() {
                userService.updateUser(request.getUserDTO());
                return new ResponseEntity(HttpStatus.OK);
            }
        }.start();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("id") final int userId, @RequestBody final ClientRequest request) {
        return new MethodExecutor(request) {
            @Override
            protected boolean isAuthenticated(ClientRequest request) {
                return sessionService.isAuthenticated(request);
            }

            @Override
            protected ResponseEntity execute() {
                userService.disableUser(userId);
                return new ResponseEntity(HttpStatus.OK);
            }
        }.start();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserDTO> get(@PathVariable("id") final int userId) {
        return new ResponseEntity<UserDTO>(userService.get(userId), HttpStatus.OK);
    }
}
