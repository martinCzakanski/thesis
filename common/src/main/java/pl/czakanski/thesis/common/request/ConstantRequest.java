package pl.czakanski.thesis.common.request;

public class ConstantRequest {

    public static final String ID = "/{id}";
    public static final String ID_PATH = "id";

    public static final String AUTHENTICATED = "/authenticated";

    public static final String ARTICLE = "/article";
    public static final String ARTICLE_MATCH = ID + "/matched";

    public static final String MESSAGE = "/message";

    public static final String SESSION = "/session";
    public static final String SESSION_REQUEST = "/request";
    public static final String SESSION_AUTHENTICATED = AUTHENTICATED;

    public static final String USER = "/user";
    public static final String USER_AUTHENTICATED = AUTHENTICATED;
    public static final String USER_ACTIVATION = ID + "/active";
}
