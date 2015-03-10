package com.flatmatesapp.jparepos;

import com.flatmatesapp.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 *
 * @author iulian.dafinoiu
 */
public interface ItemRepository extends JpaRepository<Item, Integer>, QueryDslPredicateExecutor<Item>, JpaSpecificationExecutor<Item> {
}
