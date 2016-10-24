package pl.czakanski.thesis.common.request;

public class MatchArticleRequest extends ClientRequest {

    private String matchTerm;

    public String getMatchTerm() {
        return matchTerm;
    }

    public void setMatchTerm(String matchTerm) {
        this.matchTerm = matchTerm;
    }
}
