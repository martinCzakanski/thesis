package pl.czakanski.thesis.client.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.czakanski.thesis.client.server.service.NotificationService;
import pl.czakanski.thesis.common.config.BaseAppConfig;
import pl.czakanski.thesis.common.model.User;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private MailSender mailSender;

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public void sentNotification(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("kontotestowe20160901@gmail.com");
        message.setTo(user.getEmail());
        message.setSubject("Activation link");
        //TODO Parametrized address to activate link
        message.setText("Use following link to active your account: ");
        mailSender.send(message);
    }
}
