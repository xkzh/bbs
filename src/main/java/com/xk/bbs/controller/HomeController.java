package com.xk.bbs.controller;


import com.xk.bbs.bean.PageBean;
import com.xk.bbs.bean.PostType;
import com.xk.bbs.service.PostService;
import com.xk.bbs.service.PostTypeService;
import org.apache.http.util.TextUtils;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class HomeController {

    private static final Logger log = Logger.getLogger(HomeController.class);

    @Resource
    PostService postService;

    @Resource
    PostTypeService postTypeService;

    @RequestMapping(value = "/home",method = RequestMethod.GET)
    public String main(@RequestParam("p") String p, Model model){
        log.error("  ###  p:  "+p);
//        try {
            PageBean pageBean = postService.findAllPost(TextUtils.isEmpty(p) ? 1 : Integer.valueOf(p));
            List<PostType> typeList = postTypeService.findAllPostType();
            log.error("pageBean.toString():   "+pageBean.toString());
            log.error("typeList.toString():   "+typeList.toString());
            model.addAttribute("pageBean",pageBean);
            model.addAttribute("typeList",typeList);
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//        }

        return "home";
    }

}
