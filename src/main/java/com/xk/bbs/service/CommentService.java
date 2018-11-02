package com.xk.bbs.service;

import com.xk.bbs.bean.Comment;
import com.xk.bbs.bean.PageBean;
import com.xk.bbs.bean.Post;
import com.xk.bbs.dao.BaseDao;

import java.util.List;

public interface CommentService extends BaseDao<Comment> {

    List<Comment> findPostCommentByPid(Long pid);

}
