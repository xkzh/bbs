package com.xk.bbs.controller;


import com.xk.bbs.bean.*;
import com.xk.bbs.bean.base.BaseResult;
import com.xk.bbs.service.CommentService;
import com.xk.bbs.service.PostService;
import com.xk.bbs.service.PostTypeService;
import com.xk.bbs.util.DateUtil;
import org.apache.http.util.TextUtils;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class HomeController {

    private static final Logger log = Logger.getLogger(HomeController.class);
    public static final String TAG = HomeController.class.getSimpleName();


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
            log.error(TAG+" main() method pageBean.toString():   "+pageBean.toString());
            log.error(TAG+" main() method typeList.toString():   "+typeList.toString());
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
        log.error(TAG+" posttype() method postType.toString():   "+postType.toString());
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
            log.error(TAG+"  posttype() method pageBean.toString():   "+pageBean.toString());
            log.error(TAG+"  posttype() method typeList.toString():   "+typeList.toString());
            model.addAttribute("pageBean",pageBean);
            model.addAttribute("typeList",typeList);
            model.addAttribute("postType",postType);
        }
        return "posttype";
    }

    /**
     * 跳转到帖子详情界面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/postdetail",method = RequestMethod.GET)
    public String postDetail(@RequestParam("id") String id,Model model){
        log.error(TAG+"  postDetail() method id:  "+id);
        try {
            Long pid = Long.valueOf(id);
            Post post = postService.findPostById(pid);
            log.error(TAG+"  postDetail() method post:  "+post.toString());
            if(post == null){
                HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
                response.sendError(404);
            }else{
                model.addAttribute("post",post);
                List<Comment> commentList = commentService.findPostCommentByPid(pid);
                log.error(TAG+"  postDetail() method commentList:  "+commentList.toString());
                model.addAttribute("commentList",commentList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "postdetail";
    }

    /**
     * 跳转到发帖界面
     * @param t
     * @param model
     * @return
     */
    @RequestMapping(value = "/newpost",method = RequestMethod.GET)
    public String newpost(@RequestParam("t") String t,Model model){
        log.error(TAG+" newpost() method t: "+t);
        PostType postType = postTypeService.findPostTypeByAlias(t);
        if(postType == null){
            try {
                HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
                response.sendError(404);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            model.addAttribute("pt",postType);
        }
        return "newpost";
    }

    @ResponseBody
    @RequestMapping(value = "/newpost",method = RequestMethod.POST)
    public BaseResult newpost(@RequestParam("alias") String alias,
                              @RequestParam("typeId") String typeId,
                              @RequestParam("title") String title,
                              @RequestParam("url") String url,
                              @RequestParam("content") String content){

        log.error(TAG+" newpost() method alias: "+alias);
        log.error(TAG+" newpost() method typeId: "+typeId);
        log.error(TAG+" newpost() method title: "+title);
        log.error(TAG+" newpost() method url: "+url);
        log.error(TAG+" newpost() method content: "+content);

        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        User user = (User) request.getSession().getAttribute("curr_user");
        PostType postType = postTypeService.findPostTypeByAlias(alias);
        if(user == null){
            return BaseResult.instance(0,"用户信息有误");
        }
        if(postType == null){
            return BaseResult.instance(0,"帖子类别参数有误");
        }
        Post post = new Post();
        post.setPostType(postType);
        post.setTitle(title);
        post.setUrl(url);
        post.setContent(content);
        post.setUser(user);
        post.setLastcommentstime(DateUtil.getNow());
        postService.save(post);
        log.error(TAG+" newpost() method post: "+post.toString());
        return BaseResult.success(post);
    }

}
