package com.xk.bbs.service.impl;

import com.xk.bbs.bean.User;
import com.xk.bbs.bean.example.Content;
import com.xk.bbs.controller.LoginController;
import com.xk.bbs.dao.BaseDaoImpl;
import com.xk.bbs.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl  extends BaseDaoImpl<User> implements UserService {
    private static final Logger log = Logger.getLogger(LoginController.class);

    @Override
    public User findByEmailAndPwd(String email, String pwd) {
        return (User)getSession().createQuery("FROM User where email = ?0 and password = ?1 ")
                .setParameter(0,email)
                .setParameter(1,pwd)
                .setFirstResult(0)
                .setMaxResults(1).uniqueResult();
    }
}
