package com.flatmatesapp.util.guavafunc;

import com.flatmatesapp.model.FriendlyGroup;
import com.flatmatesapp.vo.FriendlyGroupVO;
import com.google.common.base.Function;


public enum FriendlyGoupToFriendlyGroupVO implements Function<FriendlyGroup, FriendlyGroupVO> {

    INSTANCE;

    @Override
    public FriendlyGroupVO apply(FriendlyGroup group) {
        FriendlyGroupVO newGroupVO = new FriendlyGroupVO();
        
	newGroupVO.setId(group.getId());
	newGroupVO.setBudget(1337.0);
	newGroupVO.setName(group.getName());
	return newGroupVO;
    }
}
