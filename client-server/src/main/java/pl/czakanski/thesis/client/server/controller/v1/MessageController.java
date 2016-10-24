package pl.czakanski.thesis.client.server.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.czakanski.thesis.client.server.service.MessageService;
import pl.czakanski.thesis.client.server.service.SessionService;
import pl.czakanski.thesis.common.helpers.MethodExecutor;
import pl.czakanski.thesis.common.model.Session;
import pl.czakanski.thesis.common.request.ClientRequest;
import pl.czakanski.thesis.common.request.MessageRequest;

@Controller
@RequestMapping(value = "/message")
public class MessageController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private SessionService sessionService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addMessage (@RequestBody final MessageRequest request) {
        return new MethodExecutor(request) {
            @Override
            protected boolean isAuthenticated(ClientRequest request) {
                return sessionService.isAuthenticated(request);
            }

            @Override
            protected ResponseEntity<?> execute() {
                Session session = sessionService.getSession(request);
                messageService.storeMessage(session.getUserId(), request.getMessage());
                return new ResponseEntity(HttpStatus.CREATED);
            }
        }.start();
    }
}
