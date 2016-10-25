package pl.czakanski.thesis.common.helpers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.czakanski.thesis.common.request.ClientRequest;

public abstract class MethodExecutor<T extends Object> {
    private ClientRequest clientRequest;

    public MethodExecutor(ClientRequest clientRequest) {
        this.clientRequest = clientRequest;
    }

    public ResponseEntity<T> start() {
        ResponseEntity<T> response;
        if (!verifyRequest(clientRequest)) {
            response = new ResponseEntity<T>(HttpStatus.BAD_REQUEST);
        } else if (!isAuthenticated(clientRequest)) {
            response = new ResponseEntity<T>(HttpStatus.UNAUTHORIZED);
        } else {
            response = execute();
        }
        return response;
    }

    private boolean verifyRequest(ClientRequest request) {
        return request != null && request.getSessionId() != null;
    }

    protected abstract boolean isAuthenticated(ClientRequest request);
    protected abstract ResponseEntity<T> execute();
}
