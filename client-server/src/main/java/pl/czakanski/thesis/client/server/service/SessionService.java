package pl.czakanski.thesis.client.server.service;

import pl.czakanski.thesis.common.model.Session;
import pl.czakanski.thesis.common.request.ClientRequest;

public interface SessionService {
    String createSession(Integer userId);
    void destroySession(ClientRequest request);
    boolean isAuthenticated(ClientRequest request);
    Session getSession(ClientRequest request);
}
