package pl.czakanski.thesis.common.dao;

import org.springframework.data.repository.CrudRepository;
import pl.czakanski.thesis.common.model.Message;

public interface MessageDao extends CrudRepository<Message, Integer> {
}
