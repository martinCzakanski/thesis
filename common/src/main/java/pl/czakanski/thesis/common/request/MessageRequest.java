package pl.czakanski.thesis.common.request;

public class MessageRequest extends ClientRequest {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
