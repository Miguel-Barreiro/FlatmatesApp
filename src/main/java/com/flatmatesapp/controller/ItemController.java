package com.flatmatesapp.controller;

import com.flatmatesapp.service.ItemService;
import com.flatmatesapp.vo.ItemVO;
import java.util.List;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("view")
@Slf4j
public class ItemController extends AbstractController {
    private static final long serialVersionUID = 1879788974825264858L;
    
    @Autowired
    private ItemService itemService;
    
    @Getter
    private List<ItemVO> items;
    
    public void listItems() {
        this.items = this.itemService.findAllForUser(getUserDetails().getId().intValue());
    }
    
}
