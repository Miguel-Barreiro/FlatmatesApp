package com.flatmatesapp.service;

import com.flatmatesapp.vo.FriendlyGroupVO;
import java.util.List;

/**
 *
 * @author Miguel
 */
public interface FriendlyGroupService {
    public List<FriendlyGroupVO> findAllForUser(Integer userId);
}
