package pl.czakanski.thesis.nanoservice.user.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import pl.czakanski.thesis.common.helpers.MethodExecutor;
import pl.czakanski.thesis.common.helpers.NanoserviceConstant;
import pl.czakanski.thesis.common.model.User;
import pl.czakanski.thesis.common.request.*;
import pl.czakanski.thesis.nanoservice.user.service.NotificationService;
import pl.czakanski.thesis.nanoservice.user.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Controller
@RequestMapping(value = ConstantRequest.USER)
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private RestTemplate restTemplate;

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

    @RequestMapping(value = ConstantRequest.ID, method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable(ConstantRequest.ID_PATH) final int userId, @RequestBody final UserRequest request) {
        return new MethodExecutor(request) {
            @Override
            protected boolean isAuthenticated(ClientRequest request) {
                return isAuthenticatedSession(request);
            }

            @Override
            protected ResponseEntity execute() {
                userService.updateUser(request.getUserDTO());
                return new ResponseEntity(HttpStatus.OK);
            }
        }.start();
    }

    @RequestMapping(value = ConstantRequest.ID, method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable(ConstantRequest.ID_PATH) final int userId, @RequestBody final ClientRequest request) {
        return new MethodExecutor(request) {
            @Override
            protected boolean isAuthenticated(ClientRequest request) {
                return isAuthenticatedSession(request);
            }

            @Override
            protected ResponseEntity execute() {
                userService.disableUser(userId);
                return new ResponseEntity(HttpStatus.OK);
            }
        }.start();
    }

    @RequestMapping(value = ConstantRequest.ID, method = RequestMethod.GET)
    public ResponseEntity<UserDTO> get(@PathVariable(ConstantRequest.ID_PATH) final int userId) {
        return new ResponseEntity<UserDTO>(userService.get(userId), HttpStatus.OK);
    }

    @RequestMapping(value = ConstantRequest.USER_AUTHENTICATED, method = RequestMethod.POST)
    public ResponseEntity<User> getAuthenticated (@RequestBody final LoginRequest request) {
        return new ResponseEntity<User>(userService.getAuthenticatedUser(request.getEmail(), request.getPassword()), HttpStatus.OK);
    }

    private boolean isAuthenticatedSession(ClientRequest request) {
        ResponseEntity<Boolean> result = null;
        try {
            result = restTemplate.postForEntity(new URI(NanoserviceConstant.SESSION_SERVICE + ConstantRequest.SESSION + ConstantRequest.SESSION_AUTHENTICATED), request, Boolean.class);
        } catch (URISyntaxException e) {
            return false;
        }
        return result.getBody();
    }
}
