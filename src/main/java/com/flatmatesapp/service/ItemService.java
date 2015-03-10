package com.flatmatesapp.service;

import com.flatmatesapp.model.Item;
import com.flatmatesapp.vo.ItemVO;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author iulian.dafinoiu
 */
public interface ItemService extends Serializable {
    
    public Item store(ItemVO item, Integer curUserId);

    public void remove(Integer idItem);
    public Item find(Integer idItem);
    public List<Item> findAll();
    public List<Item> findAllForUser(Integer userId);
}
