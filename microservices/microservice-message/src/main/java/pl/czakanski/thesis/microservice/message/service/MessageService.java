package pl.czakanski.thesis.microservice.message.service;

public interface MessageService {
    void storeMessage(Integer userId, String message);
}
