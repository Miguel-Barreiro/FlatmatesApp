/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flatmatesapp.service.impl;

import com.flatmatesapp.jparepos.FriendlyGroupRepository;
import com.flatmatesapp.model.FriendlyGroup;
import com.flatmatesapp.model.QFriendlyGroup;
import com.flatmatesapp.model.QUserGroup;
import com.flatmatesapp.service.FriendlyGroupService;
import com.flatmatesapp.util.guavafunc.FriendlyGoupToFriendlyGroupVO;
import com.flatmatesapp.vo.FriendlyGroupVO;
import com.google.common.collect.Lists;
import com.mysema.query.types.expr.BooleanExpression;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Miguel
 */
@Service
@Transactional
@Slf4j
public class FriendlyGroupServiceImpl implements FriendlyGroupService{

    @Autowired
    private FriendlyGroupRepository friendlyGroupRepository;

    @Override
    public List<FriendlyGroupVO> findAllForUser(Integer userId) {
	
	QFriendlyGroup friendlyGroup = QFriendlyGroup.friendlyGroup;
	QUserGroup userGroup = QUserGroup.userGroup;
	
	//TODO: here we need something (connect the relational table)
	Integer hackGroupId = 1;
	BooleanExpression filterByUser = friendlyGroup.id.eq(hackGroupId);
		
	return Lists.newArrayList(this.friendlyGroupRepository.findAll(filterByUser))
				    .parallelStream().map(FriendlyGoupToFriendlyGroupVO.INSTANCE::apply)
					.collect(Collectors.toList());

    }

    
    
}
