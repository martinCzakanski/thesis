package pl.czakanski.thesis.microservice.session.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import pl.czakanski.thesis.common.helpers.MicroserviceConstant;
import pl.czakanski.thesis.common.model.Session;
import pl.czakanski.thesis.common.model.User;
import pl.czakanski.thesis.common.request.ClientRequest;
import pl.czakanski.thesis.common.request.ConstantRequest;
import pl.czakanski.thesis.common.request.LoginRequest;
import pl.czakanski.thesis.microservice.session.service.SessionService;

import java.net.URI;
import java.net.URISyntaxException;

@Controller
@RequestMapping(value = ConstantRequest.SESSION)
public class SessionController {

    @Autowired
    private SessionService sessionService;
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestBody final LoginRequest request) {
        ResponseEntity<User> user = null;
        try {
            user = restTemplate.postForEntity(new URI(MicroserviceConstant.USER_SERVICE + ConstantRequest.USER + ConstantRequest.USER_AUTHENTICATED), request, User.class);
        } catch (URISyntaxException e) {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (user != null) {
            String sessionId = sessionService.createSession(user.getBody().getUserId());
            return new ResponseEntity<String>(sessionId, HttpStatus.CREATED);
        }
        return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity logout(@RequestBody final ClientRequest request) {
        sessionService.destroySession(request);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = ConstantRequest.SESSION_AUTHENTICATED, method = RequestMethod.POST)
    public ResponseEntity<Boolean> isAuthenticated(@RequestBody final ClientRequest request) {
        return new ResponseEntity<Boolean>(sessionService.isAuthenticated(request), HttpStatus.OK);
    }

    @RequestMapping(value = ConstantRequest.SESSION_REQUEST, method = RequestMethod.POST)
    public ResponseEntity<Session> session(@RequestBody final ClientRequest request) {
        return new ResponseEntity<Session>(sessionService.getSession(request), HttpStatus.OK);
    }
}