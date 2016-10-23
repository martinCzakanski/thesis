package pl.czakanski.thesis.client.server.service;

public interface MessageService {
    void storeMessage(Integer userId, String message);
}
