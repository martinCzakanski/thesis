package pl.czakanski.thesis.client.server.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.czakanski.thesis.client.server.service.MessageService;

@Controller
@RequestMapping(value = "/user")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/{id}/message", method = RequestMethod.POST)
    public ResponseEntity activeAccount(@PathVariable("id") Integer userId, @RequestBody String message) {
        messageService.storeMessage(userId, message);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
