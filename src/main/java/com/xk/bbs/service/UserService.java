package com.xk.bbs.service;

import com.xk.bbs.bean.User;
import com.xk.bbs.dao.BaseDao;

public interface UserService extends BaseDao<User> {

    User findByEmailAndPwd(String email,String pwd);

}