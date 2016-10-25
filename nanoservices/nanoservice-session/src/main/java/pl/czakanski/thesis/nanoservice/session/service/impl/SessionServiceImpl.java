package pl.czakanski.thesis.nanoservice.session.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import pl.czakanski.thesis.common.helpers.NanoserviceConstant;
import pl.czakanski.thesis.common.model.Session;
import pl.czakanski.thesis.common.request.ClientRequest;
import pl.czakanski.thesis.common.request.ConstantRequest;
import pl.czakanski.thesis.nanoservice.session.service.SessionService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private RestTemplate restTemplate;

    private Map<String, Session> sessionMap = new HashMap<String, Session>();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createSession(Integer userId) {
        Session session = new Session();
        session.setCreateTime(new Date());
        session.setUserId(userId);
        session.setClosed(Boolean.FALSE);
        session.setSessionValue(UUID.randomUUID().toString());
        session = saveSession(session);
        sessionMap.put(session.getSessionValue(), session);
        return session.getSessionValue();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void destroySession(ClientRequest request) {
        Session session = sessionMap.get(request.getSessionId());
        if (session != null) {
            session.setUpdateTime(new Date());
            session.setClosed(Boolean.TRUE);
            session = saveSession(session);
            sessionMap.remove(session.getSessionValue());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public boolean isAuthenticated(ClientRequest request) {
        return getSession(request) != null ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Session getSession(ClientRequest request) {
        Session session = sessionMap.get(request.getSessionId());
        if (session != null) {
            updateSession(session);
        } else {
            Session databaseSession = getOpenSession(request.getSessionId());
            if(databaseSession != null) {
                updateSession(databaseSession);
                session = databaseSession;
            }
        }
        return session;
    }

    private void updateSession(Session session) {
        session.setUpdateTime(new Date());
        session = saveSession(session);
        sessionMap.put(session.getSessionValue(), session);
    }

    private Session saveSession(Session session) {
        ResponseEntity<Session> response = restTemplate.postForEntity(NanoserviceConstant.SESSION_SAVE_SERVICE + ConstantRequest.SESSION, session, Session.class);
        return response.getBody();
    }

    private Session getOpenSession(String sessionId) {
        ResponseEntity<Session> response = restTemplate.getForEntity(NanoserviceConstant.SESSION_SAVE_SERVICE + ConstantRequest.SESSION + "/" + sessionId, Session.class);
        return response.getBody();
    }
}
