package com.xk.bbs.service.impl;

import com.xk.bbs.bean.PageBean;
import com.xk.bbs.bean.Post;
import com.xk.bbs.dao.BaseDaoImpl;
import com.xk.bbs.service.PostService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PostServiceImpl extends BaseDaoImpl<Post> implements PostService {

    @Override
    public PageBean findAllPost(int page) {
        // from Post p left join  p.user left join p.postType
        // hibernate 已经给你做好了级联查询，不用你再做了，hql关键字查询怎么写，比如搜索接口
        return getPageBean(page,"from Post",new Object[]{});
    }
}
