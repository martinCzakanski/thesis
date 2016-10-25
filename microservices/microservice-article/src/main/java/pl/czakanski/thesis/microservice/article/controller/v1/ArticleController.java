package pl.czakanski.thesis.microservice.article.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import pl.czakanski.thesis.common.helpers.MethodExecutor;
import pl.czakanski.thesis.common.helpers.MicroserviceConstant;
import pl.czakanski.thesis.common.model.Article;
import pl.czakanski.thesis.common.request.ClientRequest;
import pl.czakanski.thesis.common.request.ConstantRequest;
import pl.czakanski.thesis.common.request.MatchArticleRequest;
import pl.czakanski.thesis.microservice.article.service.ArticleService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Controller
@RequestMapping(value = ConstantRequest.ARTICLE)
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = ConstantRequest.ID, method = RequestMethod.POST)
    public ResponseEntity<Article> get(@PathVariable(ConstantRequest.ID_PATH) final int articleId, @RequestBody final ClientRequest request) {
        return new MethodExecutor<Article>(request) {
            @Override
            protected boolean isAuthenticated(ClientRequest request) {
                return isAuthenticatedSession(request);
            }

            @Override
            protected ResponseEntity<Article> execute() {
                return new ResponseEntity<Article>(articleService.get(articleId), HttpStatus.OK);
            }
        }.start();
    }

    @RequestMapping(value = ConstantRequest.ARTICLE_MATCH, method = RequestMethod.POST)
    public ResponseEntity<List<String>> match(@PathVariable(ConstantRequest.ID_PATH) final int articleId, @RequestBody final MatchArticleRequest request) {
        return new MethodExecutor<List<String>>(request) {
            @Override
            protected boolean isAuthenticated(ClientRequest request) {
                return isAuthenticatedSession(request);
            }

            @Override
            protected ResponseEntity<List<String>> execute() {
                return new ResponseEntity<List<String>>(articleService.matchLines(articleId, request.getMatchTerm()), HttpStatus.OK);
            }
        }.start();
    }

    private boolean isAuthenticatedSession(ClientRequest request) {
        ResponseEntity<Boolean> result = null;
        try {
            result = restTemplate.postForEntity(new URI(MicroserviceConstant.SESSION_SERVICE + ConstantRequest.SESSION + ConstantRequest.SESSION_AUTHENTICATED), request, Boolean.class);
        } catch (URISyntaxException e) {
            return false;
        }
        return result.getBody();
    }
}