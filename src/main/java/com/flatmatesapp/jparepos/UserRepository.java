package com.flatmatesapp.jparepos;

import com.flatmatesapp.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author iulian.dafinoiu
 */
public interface UserRepository extends JpaRepository<User, Integer>, QueryDslPredicateExecutor<User>, JpaSpecificationExecutor<User> {
    List<User> findByUsername(@Param("username") String username);
}
