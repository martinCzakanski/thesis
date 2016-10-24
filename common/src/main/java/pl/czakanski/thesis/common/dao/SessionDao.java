package pl.czakanski.thesis.common.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.czakanski.thesis.common.model.Session;

public interface SessionDao extends CrudRepository<Session, Integer> {

    @Query("select s from Session s where s.closed = 0 and s.session=:session")
    Session getOpenSession(@Param("session") String session);
}
