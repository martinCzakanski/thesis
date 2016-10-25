package pl.czakanski.thesis.nanoservice.session.save.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.czakanski.thesis.common.dao.SessionDao;
import pl.czakanski.thesis.common.model.Session;
import pl.czakanski.thesis.common.request.ConstantRequest;

@Controller
@RequestMapping(value = ConstantRequest.SESSION)
public class SessionController {

    @Autowired
    private SessionDao sessionDao;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Session> save (@RequestBody final Session request) {
        return new ResponseEntity<Session>(sessionDao.save(request), HttpStatus.OK);
    }

    @RequestMapping(value = ConstantRequest.ID, method = RequestMethod.GET)
    public ResponseEntity<Session> openSession(@PathVariable(ConstantRequest.ID_PATH) final String sessionId) {
        return new ResponseEntity<Session>(sessionDao.getOpenSession(sessionId), HttpStatus.OK);
    }
}