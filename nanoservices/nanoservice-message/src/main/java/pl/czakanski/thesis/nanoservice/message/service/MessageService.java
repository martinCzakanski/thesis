package pl.czakanski.thesis.nanoservice.message.service;

public interface MessageService {
    void storeMessage(Integer userId, String message);
}
