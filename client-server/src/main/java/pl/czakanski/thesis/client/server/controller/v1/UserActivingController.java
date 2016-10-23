package pl.czakanski.thesis.client.server.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.czakanski.thesis.client.server.service.UserService;


@Controller
@RequestMapping(value = "/user")
public class UserActivingController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/{id}/active", method = RequestMethod.GET)
    public ResponseEntity activeUserAccount(@PathVariable("id") int userId) {
        userService.activeUser(userId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
