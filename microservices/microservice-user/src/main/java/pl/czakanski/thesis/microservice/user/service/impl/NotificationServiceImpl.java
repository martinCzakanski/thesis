package pl.czakanski.thesis.microservice.user.service.impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.czakanski.thesis.common.model.User;
import pl.czakanski.thesis.microservice.user.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

//    @Autowired
//    private MailSender mailSender;

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public void sentNotification(User user, String url) {
        if(user != null) {
            String urlToActivation = url + "/" + user.getUserId() + "/active";

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("kontotestowe20160901@gmail.com");
            message.setTo(user.getEmail());
            message.setSubject("Activation link");
            message.setText(String.format("Use following link to active your account: %s", urlToActivation));

//            mailSender.send(message);
        }
    }
}
