package pl.czakanski.thesis.nanoservice.message.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.czakanski.thesis.common.MessageType;
import pl.czakanski.thesis.common.dao.MessageDao;
import pl.czakanski.thesis.common.model.Message;
import pl.czakanski.thesis.nanoservice.message.service.MessageService;

import java.util.Date;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDao messageDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void storeMessage(Integer userId, String message) {
        Message messageToStore = new Message();
        messageToStore.setMessage(message);
        messageToStore.setUserId(userId);
        messageToStore.setCreatedTime(new Date());
        messageToStore.setMessageType(MessageType.BUG);
        messageDao.save(messageToStore);
    }
}
