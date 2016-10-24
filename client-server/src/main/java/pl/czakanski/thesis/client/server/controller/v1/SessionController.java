package pl.czakanski.thesis.client.server.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.czakanski.thesis.client.server.service.SessionService;
import pl.czakanski.thesis.client.server.service.UserService;
import pl.czakanski.thesis.common.model.User;
import pl.czakanski.thesis.common.request.ClientRequest;
import pl.czakanski.thesis.common.request.LoginRequest;

@Controller
@RequestMapping(value = "/session")
public class SessionController {

    @Autowired
    SessionService sessionService;
    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestBody final LoginRequest request) {
        User user = userService.getAuthenticatedUser(request.getMail(), request.getPassword());
        if(user != null) {
            String sessionId = sessionService.createSession(user.getUserId());
            return new ResponseEntity<String>(sessionId, HttpStatus.CREATED);
        }
       return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity logout(@RequestBody final ClientRequest request) {
        sessionService.destroySession(request);
        return new ResponseEntity(HttpStatus.OK);
    }
}