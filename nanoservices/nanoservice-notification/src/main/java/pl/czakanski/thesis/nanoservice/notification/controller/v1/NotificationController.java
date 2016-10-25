package pl.czakanski.thesis.nanoservice.notification.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.czakanski.thesis.common.request.ConstantRequest;
import pl.czakanski.thesis.common.request.NotificationRequest;
import pl.czakanski.thesis.nanoservice.notification.service.NotificationService;

@Controller
@RequestMapping(value = ConstantRequest.NOTIFICATION)
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity send(@RequestBody final NotificationRequest request) {
        notificationService.sentNotification(request.getUser(), request.getUrl());
        return new ResponseEntity(HttpStatus.OK);
    }
}
