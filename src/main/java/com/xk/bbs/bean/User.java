package com.xk.bbs.bean;

import com.google.gson.Gson;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.util.TextUtils;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "t_user")
@Entity
public class User implements Serializable {
    private int id;
    private String nickname;
    private String realname;
    private String qianming;
    private String qq;
    private String twitter;
    private String email;
    private String github;
    private String weibo;
    private String blog;
    private String location;
    private String password;
    private String pic;
    private String regtime;
    private String lasttime;
    private String lastip;
    private boolean action;
    private boolean enable;
    private String role;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return TextUtils.isEmpty(nickname) ? "" : nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRealname() {

        return TextUtils.isEmpty(realname) ? "" : realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getQianming() {
        return TextUtils.isEmpty(qianming) ? "" : qianming;
    }

    public void setQianming(String qianming) {
        this.qianming = qianming;
    }

    public String getQq() {
        return TextUtils.isEmpty(qq) ? "" : qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getTwitter() {
        return TextUtils.isEmpty(twitter) ? "" : twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getEmail() {
        return TextUtils.isEmpty(email) ? "" : email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGithub() {
        return TextUtils.isEmpty(github) ? "" : github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getWeibo() {
        return TextUtils.isEmpty(weibo) ? "" : weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    public String getBlog() {
        return TextUtils.isEmpty(blog) ? "" : blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public String getLocation() {
        return TextUtils.isEmpty(location) ? "" : location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPassword() {
        return TextUtils.isEmpty(password) ? "" : password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPic() {
        return TextUtils.isEmpty(pic) ? "" : pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getRegtime() {
        return TextUtils.isEmpty(regtime) ? "" : regtime;
    }

    public void setRegtime(String regtime) {
        this.regtime = regtime;
    }

    public String getLasttime() {
        return TextUtils.isEmpty(lasttime) ? "" : lasttime;
    }

    public void setLasttime(String lasttime) {
        this.lasttime = lasttime;
    }

    public String getLastip() {
        return TextUtils.isEmpty(lastip) ? "" : lastip;
    }

    public void setLastip(String lastip) {
        this.lastip = lastip;
    }

    public boolean isAction() {
        return action;
    }

    public void setAction(boolean action) {
        this.action = action;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getRole() {
        return TextUtils.isEmpty(role) ? "" : role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
