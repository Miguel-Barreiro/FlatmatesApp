package com.flatmatesapp.service.impl;

import com.flatmatesapp.jparepos.ItemRepository;
import com.flatmatesapp.jparepos.UserRepository;
import com.flatmatesapp.model.Item;
import com.flatmatesapp.model.User;
import com.flatmatesapp.service.ItemService;
import com.flatmatesapp.vo.ItemVO;
import java.util.List;
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
    public Item store(ItemVO itemVO, Integer curUserId) {
        User user = this.userRepository.findOne(curUserId);
        
        Item item = new Item();
        
        this.itemRepository.saveAndFlush(item);
        return item;
    }

    @Override
    public void remove(Integer itemId) {
        this.itemRepository.delete(itemId);
    }

    @Override
    public Item find(Integer itemId) {
        return this.itemRepository.findOne(itemId);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<Item> findAll() {
        return this.itemRepository.findAll();
    }

    @Override
    public List<Item> findAllForUser(Integer userId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
