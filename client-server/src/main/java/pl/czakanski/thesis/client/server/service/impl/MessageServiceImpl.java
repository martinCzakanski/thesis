package pl.czakanski.thesis.client.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.czakanski.thesis.client.server.service.MessageService;
import pl.czakanski.thesis.client.server.service.UserService;
import pl.czakanski.thesis.common.MessageType;
import pl.czakanski.thesis.common.dao.MessageDao;
import pl.czakanski.thesis.common.model.Message;

import java.util.Date;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private UserService userService;
    @Autowired
    private MessageDao messageDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void storeMessage(Integer userId, String message) {
        if(userService.existUser(userId)) {
            Message messageToStore = new Message();
            messageToStore.setMessage(message);
            messageToStore.setUserId(userId);
            messageToStore.setCreatedTime(new Date());
            messageToStore.setMessageType(MessageType.BUG);
            messageDao.save(messageToStore);
        }
    }
}
