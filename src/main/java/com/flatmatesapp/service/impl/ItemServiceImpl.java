package com.flatmatesapp.service.impl;

import com.flatmatesapp.jparepos.ItemRepository;
import com.flatmatesapp.jparepos.UserRepository;
import com.flatmatesapp.model.Item;
import com.flatmatesapp.model.QItem;
import com.flatmatesapp.model.User;
import com.flatmatesapp.service.ItemService;
import com.flatmatesapp.util.guavafunc.ItemToItemVO;
import com.flatmatesapp.vo.ItemVO;
import com.google.common.collect.Lists;
import com.mysema.query.types.expr.BooleanExpression;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class ItemServiceImpl implements ItemService {
    private static final long serialVersionUID = 15123456404105000L;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ItemRepository itemRepository;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public ItemVO store(ItemVO itemVO, Integer curUserId) {
        User user = this.userRepository.findOne(curUserId);
        
        Item item = new Item();
        // INSERT ACTUAL STORE SHIT
        
        this.itemRepository.saveAndFlush(item);
        return itemVO;
    }

    @Override
    public void remove(Integer itemId) {
        this.itemRepository.delete(itemId);
    }

    @Override
    public ItemVO find(Integer itemId) {
        return ItemToItemVO.INSTANCE.apply(this.itemRepository.findOne(itemId));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<ItemVO> findAll() {
        return Lists.newArrayList(this.itemRepository.findAll()).parallelStream().map(ItemToItemVO.INSTANCE::apply).collect(Collectors.toList());
    }

    @Override
    public List<ItemVO> findAllForUser(Integer userId) {
        QItem item = QItem.item;
        BooleanExpression filterItemByUser = item.user.id.eq(userId);
        return Lists.newArrayList(this.itemRepository.findAll(filterItemByUser)).parallelStream().map(ItemToItemVO.INSTANCE::apply).collect(Collectors.toList());
    }
    
}
