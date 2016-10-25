package pl.czakanski.thesis.client.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.czakanski.thesis.client.server.service.SessionService;
import pl.czakanski.thesis.common.dao.SessionDao;
import pl.czakanski.thesis.common.model.Session;
import pl.czakanski.thesis.common.request.ClientRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private SessionDao sessionDao;

    private Map<String, Session> sessionMap = new HashMap<String, Session>();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createSession(Integer userId) {
        Session session = new Session();
        session.setCreateTime(new Date());
        session.setUserId(userId);
        session.setClosed(Boolean.FALSE);
        session.setSessionValue(UUID.randomUUID().toString());
        sessionDao.save(session);
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
            sessionDao.save(session);
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
            Session databaseSession = sessionDao.getOpenSession(request.getSessionId());
            if(databaseSession != null) {
                updateSession(databaseSession);
                session = databaseSession;
            }
        }
        return session;
    }

    private void updateSession(Session session) {
        session.setUpdateTime(new Date());
        sessionDao.save(session);
        sessionMap.put(session.getSessionValue(), session);
    }
}
