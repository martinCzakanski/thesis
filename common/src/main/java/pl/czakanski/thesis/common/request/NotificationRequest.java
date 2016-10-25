package pl.czakanski.thesis.common.request;

import pl.czakanski.thesis.common.model.User;

public class NotificationRequest {

    private User user;
    private String url;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
