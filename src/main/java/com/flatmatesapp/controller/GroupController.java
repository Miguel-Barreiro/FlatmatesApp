package com.flatmatesapp.controller;

import com.flatmatesapp.service.FriendlyGroupService;
import com.flatmatesapp.vo.FriendlyGroupVO;
import java.util.List;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("view")
@Slf4j
public class GroupController extends AbstractController {
    
    @Autowired
    private FriendlyGroupService groupService;
    
    @Getter
    private List<FriendlyGroupVO> groups;
    
    public void fetchGroups() {
        this.groups = this.groupService.findAllForUser(getUserDetails().getId().intValue());
    }
    
}
