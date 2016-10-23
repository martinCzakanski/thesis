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

import java.util.List;

@Controller
@RequestMapping(value = "/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/{id}/matched", method = RequestMethod.GET)
    public ResponseEntity<List<String>> match(@PathVariable("id") int articleId, @RequestBody String searchTerm) {
        return new ResponseEntity<List<String>>(articleService.matchLines(articleId, searchTerm), HttpStatus.OK);
    }
}
