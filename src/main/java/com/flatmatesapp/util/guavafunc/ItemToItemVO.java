package com.flatmatesapp.util.guavafunc;

import com.flatmatesapp.model.Item;
import com.flatmatesapp.vo.ItemVO;
import com.google.common.base.Function;

/**
 *
 * @author iulian.dafinoiu
 */
public enum ItemToItemVO implements Function<Item, ItemVO> {

    INSTANCE;

    @Override
    public ItemVO apply(Item item) {
        ItemVO itemVO = new ItemVO();
        itemVO.setAddedDate(item.getAddedDate());
        itemVO.setId(item.getId());
        if (item.getSpending() != null) {
            itemVO.setIdSpending(item.getSpending().getId());
        }
        itemVO.setIdUser(item.getUser().getId());
        itemVO.setName(item.getName());
        itemVO.setRecurrentInterval(item.getRecurrentInterval());
        itemVO.setStartingDate(item.getStartingDate());
        return itemVO;
    }
}
