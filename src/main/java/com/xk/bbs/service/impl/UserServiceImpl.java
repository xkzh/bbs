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
        return (User)getSession().createQuery("from User where email = ?0 and password = ?1 ")
                .setParameter(0,email)
                .setParameter(1,pwd)
                .setFirstResult(0)
                .setMaxResults(1).uniqueResult();
    }

    @Override
    public User findUserById(String id) {
        return (User)getSession().createQuery("from User where ic = ?0")
                .setParameter(0,id)
                .uniqueResult();
    }

    @Override
    public User findUserByNickname(String nickName) {
        return (User)getSession().createQuery("from User where nickname = ?0")
                .setParameter(0,nickName)
                .uniqueResult();
    }

    @Override
    public void updatePassword(User user) {
        update(user);
//        getSession().createQuery("update User u set u.password = ?0 where u.nickname = ?1 ").setParameter(0,password).setParameter(1,nickname).uniqueResult();
    }
}
