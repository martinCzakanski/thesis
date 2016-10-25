package pl.czakanski.thesis.nanoservice.notification.service;

import pl.czakanski.thesis.common.model.User;

public interface NotificationService {

    void sentNotification(User user, String url);
}
