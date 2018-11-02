package com.xk.bbs.controller;


import com.xk.bbs.bean.Comment;
import com.xk.bbs.bean.PageBean;
import com.xk.bbs.bean.Post;
import com.xk.bbs.bean.PostType;
import com.xk.bbs.service.CommentService;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class HomeController {

    private static final Logger log = Logger.getLogger(HomeController.class);

    @Resource
    PostService postService;

    @Resource
    PostTypeService postTypeService;

    @Resource
    CommentService commentService;

    /**
     * 跳转到主页
     * @param p
     * @param model
     * @return
     */
    @RequestMapping(value = "/home",method = RequestMethod.GET)
    public String main(@RequestParam("p") String p, Model model){
        log.error("  ###  p:  "+p);
        try {
            PageBean pageBean = postService.findAllPost(TextUtils.isEmpty(p) ? 1 : Integer.valueOf(p));
            List<PostType> typeList = postTypeService.findAllPostType();
            log.error("main() method pageBean.toString():   "+pageBean.toString());
            log.error("main() method typeList.toString():   "+typeList.toString());
            model.addAttribute("pageBean",pageBean);
            model.addAttribute("typeList",typeList);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "home";
    }


    /**
     * 点击版块获取该版块的列表
     * @param t
     * @param p
     * @param model
     * @return
     */
    @RequestMapping(value = "/posttype",method = RequestMethod.GET)
    public String posttype(@RequestParam("t") String t,@RequestParam("p") String p,Model model){ // @RequestParam("t") String t,@RequestParam("p") String p,Model model
        PostType postType = postTypeService.findPostTypeByAlias(t);
        log.error("posttype() method postType.toString():   "+postType.toString());
        if(postType == null){
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            try {
                response.sendError(404);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            // 这里肯定是要查出来分页的
            PageBean pageBean = postService.findPostByAlias(1,postType.getId());
            List<PostType> typeList = postTypeService.findAllPostType();
            log.error("posttype() method pageBean.toString():   "+pageBean.toString());
            log.error("posttype() method typeList.toString():   "+typeList.toString());
            model.addAttribute("pageBean",pageBean);
            model.addAttribute("typeList",typeList);
            model.addAttribute("postType",postType);
        }
        return "posttype";
    }

    @RequestMapping(value = "/postdetail",method = RequestMethod.GET)
    public String postDetail(@RequestParam("id") String id,Model model){
        log.error("postDetail() method id:  "+id);
        try {
            Long pid = Long.valueOf(id);
            Post post = postService.findPostById(pid);
            log.error("postDetail() method post:  "+post.toString());
            if(post == null){
                HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
                response.sendError(404);
            }else{
                model.addAttribute("post",post);
                List<Comment> commentList = commentService.findPostCommentByPid(pid);
                log.error("postDetail() method commentList:  "+commentList.toString());
                model.addAttribute("commentList",commentList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "postdetail";
    }
}
