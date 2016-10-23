package pl.czakanski.thesis.client.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.czakanski.thesis.client.server.service.ArticleService;
import pl.czakanski.thesis.common.dao.ArticleDao;
import pl.czakanski.thesis.common.model.Article;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Override
    public List<String> matchLines(Integer articleId, String searchTerm) {
        List<String> matches = new ArrayList<String>();
        Article article = articleDao.findOne(articleId);
        if(article != null && article.getText() != null) {
            for(String string : article.getText().split("\\r?\\n")) {
                if(string.contains(searchTerm)) {
                    matches.add(string);
                }
            }
        }
        return matches;
    }
}
