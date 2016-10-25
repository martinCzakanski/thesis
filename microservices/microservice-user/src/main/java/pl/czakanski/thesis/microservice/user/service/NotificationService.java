package pl.czakanski.thesis.microservice.user.service;

import pl.czakanski.thesis.common.model.User;

public interface NotificationService {

    void sentNotification(User user, String url);
}
