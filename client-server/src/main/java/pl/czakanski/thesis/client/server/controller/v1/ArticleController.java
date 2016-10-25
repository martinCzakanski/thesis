package pl.czakanski.thesis.client.server.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.czakanski.thesis.client.server.service.ArticleService;
import pl.czakanski.thesis.client.server.service.SessionService;
import pl.czakanski.thesis.common.helpers.MethodExecutor;
import pl.czakanski.thesis.common.model.Article;
import pl.czakanski.thesis.common.request.ClientRequest;
import pl.czakanski.thesis.common.request.MatchArticleRequest;

import java.util.List;

@Controller
@RequestMapping(value = "/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private SessionService sessionService;

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseEntity<Article> get(@PathVariable("id") final int articleId, @RequestBody final ClientRequest request) {
        return new MethodExecutor<Article>(request) {
            @Override
            protected boolean isAuthenticated(ClientRequest request) {
                return sessionService.isAuthenticated(request);
            }

            @Override
            protected ResponseEntity<Article> execute() {
                return new ResponseEntity<Article>(articleService.get(articleId), HttpStatus.OK);
            }
        }.start();
    }

    @RequestMapping(value = "/{id}/matched", method = RequestMethod.POST)
    public ResponseEntity<List<String>> match(@PathVariable("id") final int articleId, @RequestBody final MatchArticleRequest request) {
        return new MethodExecutor<List<String>>(request) {
            @Override
            protected boolean isAuthenticated(ClientRequest request) {
                return sessionService.isAuthenticated(request);
            }

            @Override
            protected ResponseEntity<List<String>> execute() {
                return new ResponseEntity<List<String>>(articleService.matchLines(articleId, request.getMatchTerm()), HttpStatus.OK);
            }
        }.start();
    }
}