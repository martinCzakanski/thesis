package pl.czakanski.thesis.nanoservice.user.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.czakanski.thesis.common.request.ConstantRequest;
import pl.czakanski.thesis.nanoservice.user.service.UserService;


@Controller
@RequestMapping(value = ConstantRequest.USER)
public class UserActivingController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = ConstantRequest.USER_ACTIVATION, method = RequestMethod.GET)
    public ResponseEntity activeUserAccount(@PathVariable(ConstantRequest.ID_PATH) final int userId) {
        userService.activeUser(userId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
