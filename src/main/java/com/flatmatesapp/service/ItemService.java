package com.flatmatesapp.service;

import com.flatmatesapp.vo.ItemVO;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author iulian.dafinoiu
 */
public interface ItemService extends Serializable {
    
    public ItemVO store(ItemVO item, Integer curUserId);

    public void remove(Integer idItem);
    public ItemVO find(Integer idItem);
    public List<ItemVO> findAll();
    public List<ItemVO> findAllForUser(Integer userId);
}
