package com.xk.bbs.service.impl;

import com.xk.bbs.bean.Comment;
import com.xk.bbs.bean.PageBean;
import com.xk.bbs.dao.BaseDaoImpl;
import com.xk.bbs.service.CommentService;
import com.xk.bbs.util.HqlHelper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CommentServiceImpl extends BaseDaoImpl<Comment> implements CommentService {
    @Override
    public List<Comment> findPostCommentByPid(Long pid) {
        return getSession().createQuery("From Comment c where c.post_id = "+pid).list();
    }
}
