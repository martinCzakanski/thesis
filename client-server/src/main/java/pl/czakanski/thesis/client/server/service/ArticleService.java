package pl.czakanski.thesis.client.server.service;

import java.util.List;

public interface ArticleService {
    List<String> matchLines(Integer articleId, String searchTerm);
}
