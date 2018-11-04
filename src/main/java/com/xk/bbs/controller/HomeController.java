package com.xk.bbs.controller;


import com.xk.bbs.bean.*;
import com.xk.bbs.bean.base.BaseResult;
import com.xk.bbs.service.*;
import com.xk.bbs.util.CommonUtil;
import org.apache.http.util.TextUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Resource
    UserService userService;

    @Resource
    NotifyService notifyService;

    /**
     * 跳转到主页
     * @param p
     * @param model
     * @return
     */
    @RequestMapping(value = "/home",method = RequestMethod.GET)
    public String main(@RequestParam("p") String p, Model model){
        log.error(TAG+" main() method  p:  "+p);
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

    /**
     * 发帖
     * @param alias
     * @param typeId
     * @param title
     * @param url
     * @param content
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/newpost",method = RequestMethod.POST)
    public BaseResult newpost(@RequestParam("alias") String alias,
                              @RequestParam("typeId") String typeId,
                              @RequestParam("title") String title,
                              @RequestParam("url") String url,
                              @RequestParam("content") String content){

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
        post.setLastcommentstime(CommonUtil.getNow());
        postService.save(post);
        log.error(TAG+" newpost() method post: "+post.toString());
        return BaseResult.success(post);
    }

    /**
     * 对帖子发表评论
     * @param postId
     * @param content
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public BaseResult comment(@RequestParam("postId") String postId,@RequestParam("content") String content){

        log.error(TAG+" comment() method alias: "+postId);
        log.error(TAG+" comment() method typeId: "+content);
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        User sendUser = (User) request.getSession().getAttribute("curr_user");
        // TODO 帖子里实体 评论数+1，更新评论的时间
        // TODO 如果有@的用户，找出来保存到t_表里
        // 回复数加1
        Post post = postService.findPostById(postId+"");
        post.setCommentsnum(post.getCommentsnum() + 1);
        post.setLastcommentstime(CommonUtil.getNow());
        postService.update(post);

        // 找出@用户
        String reg = "@\\w+\\s";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(content);

        if (!m.find(0)) {
            Notify notify = new Notify();
            notify.setMessage("<a href=${pageContext.request.contextPath}/account-info?p=1&u="
                    + sendUser.getNickname() + "'>"
                    + sendUser.getNickname()
                    + "</a>在您的帖子<a href=${pageContext.request.contextPath}/postdetail?id=" + post.getId()
                    + "'>" + post.getTitle() + "</a>中回复了信息，快去查看吧！");
            notify.setUser_id(sendUser.getId());
            notify.setUnread(false);
            notifyService.save(notify);
        } else {
            do {
                String nickname = m.group();
                content = content.replace(nickname, "<a href=/user.do?u="
                        + nickname.substring(1).trim() + "'>" + nickname
                        + "</a>");

                nickname = nickname.substring(1);
                // 查找@的人
                User user = userService.findUserByNickname(nickname.trim());
                if (user != null) {
                    Notify notify = new Notify();
                    notify.setMessage("<a href=${pageContext.request.contextPath}//account-info?p=1&u="
                            + user.getNickname() + "'>"
                            + user.getNickname()
                            + "</a>在帖子<a href=/postdetail?id=" + post.getId()
                            + "'>" + post.getTitle() + "</a>中提到了你");
                    notify.setUser_id(user.getId());
                    notify.setUnread(false);
                    notifyService.save(notify);
                }
            }while (m.find()) ;
        }
        Comment comment = new Comment();
        try {
            comment.setPost_id(Integer.valueOf(postId));
            comment.setContent(content);
            comment.setUser(sendUser);
            comment.setCreatetime(CommonUtil.getNow());
            commentService.save(comment);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return BaseResult.success(comment);
    }


    /**
     * 跳转到个人主页界面
     * @param username
     * @param p
     * @param model
     * @return
     */
    @RequestMapping(value = "/account-info",method = RequestMethod.GET)
    public String account_info(@RequestParam("u") String username,@RequestParam("p") String p,Model model){
        log.error(TAG+" account_info() method username:   "+username);
        log.error(TAG+" account_info() method p:   "+p);
        if(TextUtils.isEmpty(username)){
            try {
                HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
                response.sendError(404);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        User user = userService.findUserByNickname(username);
        if(user == null){
            try {
                HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
                response.sendError(404);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            int page = Integer.valueOf(p);
            PageBean pageBean = postService.findPostByUserId(page,user.getId());
            log.error(TAG+"  account_info() method pageBean.toString():   "+pageBean.toString());
            model.addAttribute("pageBean",pageBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("user",user);
        return "account-info";
    }


    /**
     * 跳转到账户设置界面
     * @param model
     * @return
     */
    @RequestMapping(value = "/account-setting",method = RequestMethod.GET)
    public String account_setting(Model model){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
        User sessionUser = (User) request.getSession().getAttribute("curr_user");
        User user = userService.findUserByNickname(sessionUser.getNickname());
        if(user == null){
            try {
                response.sendError(404);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("user",user);
        return "account-setting";
    }

    /**
     * 修改用户信息
     * @param userid
     * @param email
     * @param qianming
     * @param qq
     * @param twitter
     * @param github
     * @param weibo
     * @param blog
     * @param location
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/account-setting",method = RequestMethod.POST)
    public BaseResult account_setting(@RequestParam("userid") String userid,
                                      @RequestParam("email") String email,
                                      @RequestParam("qianming") String qianming,
                                      @RequestParam("qq") String qq,
                                      @RequestParam("twitter") String twitter,
                                      @RequestParam("github") String github,
                                      @RequestParam("weibo") String weibo,
                                      @RequestParam("blog") String blog,
                                      @RequestParam("location") String location){


        log.error(TAG+" account_setting() method userid: "+userid);
        log.error(TAG+" account_setting() method email: "+email);
        log.error(TAG+" account_setting() method qianming: "+qianming);
        log.error(TAG+" account_setting() method qq: "+qq);
        log.error(TAG+" account_setting() method twitter: "+twitter);
        log.error(TAG+" account_setting() method github: "+github);
        log.error(TAG+" account_setting() method weibo: "+weibo);
        log.error(TAG+" account_setting() method blog: "+blog);
        log.error(TAG+" account_setting() method location: "+location);

        User user = new User();
        user.setId(Integer.valueOf(userid));
        user.setEmail(email);
        user.setQianming(qianming);
        user.setQq(qq);
        user.setTwitter(twitter);
        user.setGithub(github);
        user.setWeibo(weibo);
        user.setBlog(blog);
        user.setLocation(location);
        userService.update(user);

        return BaseResult.success(user);
    }

    /***
     * 账户设置重置密码
     * @param oldpassword
     * @param password
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/resetpassword",method = RequestMethod.POST)
    public BaseResult resetPassword(@RequestParam("oldpassword") String oldpassword,
                                    @RequestParam("password") String password){

        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        User sessionUser = (User) request.getSession().getAttribute("curr_user");
        User user = userService.findUserByNickname(sessionUser.getNickname());

        log.error(TAG+" resetPassword() method oldpassword: "+oldpassword);
        log.error(TAG+" resetPassword() method password: "+password);

        if(user == null){
            return BaseResult.instance(0,"用户信息参数有误");
        }

        if(user.getPassword().equals(CommonUtil.SHA1(oldpassword))){
            user.setPassword(CommonUtil.SHA1(oldpassword));
            userService.updatePassword(user);
            return BaseResult.success();
        }else{
            return BaseResult.instance(0,"原始密码错误");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/uploadavatar",method = RequestMethod.POST)
    public BaseResult upload(@RequestParam("file") CommonsMultipartFile file){ // CommonsMultipartFile

        log.error(TAG+" upload() method ");
        try {
            log.error(TAG+" upload() method fileName："+file.getOriginalFilename());
            HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
            ServletContext servletContext = request.getServletContext();
             //获取服务器下的upload目录
            String realPath = servletContext.getRealPath("/upload");
            log.error(TAG+" upload() method realPath："+realPath);
            File filePath = new File(realPath);
            log.error(TAG+" upload() method filePath："+filePath.getAbsolutePath());
            //如果目录不存在，则创建该目录
            if (!filePath.exists()) {
                filePath.mkdir();
             }

            long  startTime = System.currentTimeMillis();

            String path = realPath+new Date().getTime()+file.getOriginalFilename();

            File newFile=new File(path);
            //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
            file.transferTo(newFile);
            long  endTime = System.currentTimeMillis();
            log.error(TAG+" upload() method 文件上传时长："+String.valueOf(endTime-startTime)+"ms");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return BaseResult.instance(0,"上传失败!");

    }

}
