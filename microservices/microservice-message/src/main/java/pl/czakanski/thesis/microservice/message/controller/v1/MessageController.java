package pl.czakanski.thesis.microservice.message.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import pl.czakanski.thesis.common.helpers.MethodExecutor;
import pl.czakanski.thesis.common.helpers.MicroserviceConstant;
import pl.czakanski.thesis.common.model.Session;
import pl.czakanski.thesis.common.request.ClientRequest;
import pl.czakanski.thesis.common.request.ConstantRequest;
import pl.czakanski.thesis.common.request.MessageRequest;
import pl.czakanski.thesis.microservice.message.service.MessageService;

import java.net.URI;
import java.net.URISyntaxException;

@Controller
@RequestMapping(value = ConstantRequest.MESSAGE)
public class MessageController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addMessage (@RequestBody final MessageRequest request) {
        return new MethodExecutor(request) {
            @Override
            protected boolean isAuthenticated(ClientRequest request) {
                return isAuthenticatedSession(request);
            }

            @Override
            protected ResponseEntity<?> execute() {
                messageService.storeMessage(getUserId(request), request.getMessage());
                return new ResponseEntity(HttpStatus.CREATED);
            }
        }.start();
    }

    private boolean isAuthenticatedSession(ClientRequest request) {
        ResponseEntity<Boolean> result = null;
        try {
            result = restTemplate.postForEntity(new URI(MicroserviceConstant.SESSION_SERVICE + ConstantRequest.SESSION + ConstantRequest.SESSION_AUTHENTICATED), request, Boolean.class);
        } catch (URISyntaxException e) {
            return false;
        }
        return result.getBody();
    }

    private Integer getUserId(ClientRequest request) {
        ResponseEntity<Session> result = null;
        try {
            result = restTemplate.postForEntity(new URI(MicroserviceConstant.SESSION_SERVICE + ConstantRequest.SESSION + ConstantRequest.SESSION_REQUEST), request, Session.class);
        } catch (URISyntaxException e) {
            return null;
        }
        return result.getBody().getUserId();
    }
}
