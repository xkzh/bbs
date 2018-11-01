package com.xk.bbs.controller;


import com.xk.bbs.bean.User;
import com.xk.bbs.bean.base.BaseResult;
import com.xk.bbs.bean.base.ResponseCode;
import com.xk.bbs.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class LoginController {

    private static final Logger log = Logger.getLogger(LoginController.class);

    @Resource
    UserService userService;

    /**
     * 跳转登录界面 // TODO 为啥会执行3次
     * @return
     */
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String login(){
        log.error("跳转登录界面%%%%%%%%%%%%%%%%");
        return "login";
    }

    @ResponseBody
    @RequestMapping(value="/login",method = RequestMethod.POST)
    public BaseResult login(@RequestParam String email, @RequestParam String password){
        User user = userService.findByEmailAndPwd(email,password);
        log.error("user：  "+user);
        if (user == null) {
            return BaseResult.instance(ResponseCode.password_incorrect.getCode(), ResponseCode.password_incorrect.getMsg());
        }else if (!user.isAction()) {
            return BaseResult.instance(ResponseCode.disactivated_account.getCode(), ResponseCode.disactivated_account.getMsg());
        }else if (!user.isEnable()) {
            return BaseResult.instance(ResponseCode.forbidden_account.getCode(), ResponseCode.forbidden_account.getMsg());
        }
        return BaseResult.success(user);
    }
}
