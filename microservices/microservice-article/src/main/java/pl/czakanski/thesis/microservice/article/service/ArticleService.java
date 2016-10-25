package pl.czakanski.thesis.microservice.article.service;

import pl.czakanski.thesis.common.model.Article;

import java.util.List;

public interface ArticleService {
    Article get(Integer articleId);
    List<String> matchLines(Integer articleId, String searchTerm);
}
