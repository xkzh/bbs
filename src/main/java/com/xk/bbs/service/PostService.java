package com.xk.bbs.service;

import com.xk.bbs.bean.PageBean;
import com.xk.bbs.bean.Post;
import com.xk.bbs.dao.BaseDao;

public interface PostService extends BaseDao<Post> {

    PageBean findAllPost(int page);

    PageBean findPostByAlias(int page,int typeId);

    Post findPostById(Long id);
}
