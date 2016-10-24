package pl.czakanski.thesis.client.server.service;

import pl.czakanski.thesis.common.model.Article;

import java.util.List;

public interface ArticleService {
    Article get(Integer articleId);
    List<String> matchLines(Integer articleId, String searchTerm);
}
