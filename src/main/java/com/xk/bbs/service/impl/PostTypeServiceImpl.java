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

    @Override
    public PostType findPostTypeByAlias(String alias) {
        // .uniqueResult()  这个不能少，否则查不到结果
        return (PostType) getSession().createQuery("from PostType where alias = ?0 ").setParameter(0,alias).uniqueResult();
    }
}
