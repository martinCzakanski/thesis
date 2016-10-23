package pl.czakanski.thesis.common.dao;

import org.springframework.data.repository.CrudRepository;
import pl.czakanski.thesis.common.model.Article;

public interface ArticleDao extends CrudRepository<Article, Integer> {
}
