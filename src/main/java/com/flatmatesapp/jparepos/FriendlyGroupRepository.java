package com.flatmatesapp.jparepos;

import com.flatmatesapp.model.FriendlyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface FriendlyGroupRepository extends JpaRepository<FriendlyGroup, Integer>, QueryDslPredicateExecutor<FriendlyGroup>, JpaSpecificationExecutor<FriendlyGroup> {
}
