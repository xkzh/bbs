package com.xk.bbs.service.impl;

import com.xk.bbs.bean.PageBean;
import com.xk.bbs.bean.Post;
import com.xk.bbs.dao.BaseDaoImpl;
import com.xk.bbs.service.PostService;
import com.xk.bbs.util.HqlHelper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PostServiceImpl extends BaseDaoImpl<Post> implements PostService {

    Logger log = Logger.getLogger(PostServiceImpl.class);

    @Override
    public PageBean findAllPost(int page) {
        // from Post p left join  p.user left join p.postType
        // hibernate 已经给你做好了级联查询，不用你再做了，hql关键字查询怎么写，比如搜索接口
        return getPageBean(page,"from Post",new Object[]{});
    }

    @Override
    public PageBean findPostByAlias(int page, int typeId) {
        return getPageBean(page,new HqlHelper(Post.class).addCondition("o.postType.id = "+typeId));
    }

    @Override
    public Post findPostById(Long id) {
//        Post post = (Post)getById(id);
//        Post post = (Post)getSession().createQuery("from Post p where p.id = "+id).uniqueResult();
        return (Post)getSession().createQuery("from Post p where p.id = "+id).uniqueResult(); //当确定返回的实例只有一个或者null时 用uniqueResult()方法
    }
}
