package com.xk.bbs.service;

import com.xk.bbs.bean.PostType;
import com.xk.bbs.dao.BaseDao;

import java.util.List;

public interface PostTypeService extends BaseDao<PostType> {

    List<PostType> findAllPostType();

    PostType findPostTypeByAlias(String alias);
}
