package com.xk.bbs.service.impl;

import com.xk.bbs.bean.PostType;
import com.xk.bbs.dao.BaseDaoImpl;
import com.xk.bbs.service.PostTypeService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PostTypeServiceImpl extends BaseDaoImpl<PostType> implements PostTypeService {
    @Override
    public List<PostType> findAllPostType() {
        return findAll();
    }
}
