package pl.czakanski.thesis.nanoservice.user.activation.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import pl.czakanski.thesis.common.helpers.NanoserviceConstant;
import pl.czakanski.thesis.common.request.ConstantRequest;


@Controller
@RequestMapping(value = ConstantRequest.USER)
public class UserActivingController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = ConstantRequest.USER_ACTIVATION, method = RequestMethod.GET)
    public ResponseEntity activeUserAccount(@PathVariable(ConstantRequest.ID_PATH) final int userId) {
        return restTemplate.getForEntity(NanoserviceConstant.USER_SERVICE + ConstantRequest.USER +"/" + userId + ConstantRequest.USER_ACTIVE, null);
    }
}
