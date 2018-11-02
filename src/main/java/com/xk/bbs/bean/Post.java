package com.xk.bbs.bean;

import com.google.gson.Gson;

import javax.persistence.*;

@Table(name = "t_post")
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String title;
	private String url;
	private String content;
	private String createtime;
	private int viewnum;
	private int commentsnum;
	private String lastcommentstime;
	private int likenum;
	private boolean toppost;
	private boolean enable;
    @OneToOne
    @JoinColumn(name="user_id",unique=true)
	private User user; // 一个帖子对应一个作者
    @OneToOne
    @JoinColumn(name="type_id",unique=true)
	private PostType postType; // 一个帖子对应一个类型




	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public int getViewnum() {
		return viewnum;
	}
	public void setViewnum(int viewnum) {
		this.viewnum = viewnum;
	}
	public int getCommentsnum() {
		return commentsnum;
	}
	public void setCommentsnum(int commentsnum) {
		this.commentsnum = commentsnum;
	}
	public String getLastcommentstime() {
		return lastcommentstime;
	}
	public void setLastcommentstime(String lastcommentstime) {
		this.lastcommentstime = lastcommentstime;
	}
	public int getLikenum() {
		return likenum;
	}
	public void setLikenum(int likenum) {
		this.likenum = likenum;
	}
	public boolean isToppost() {
		return toppost;
	}
	public void setToppost(boolean toppost) {
		this.toppost = toppost;
	}

	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public PostType getPostType() {
		return postType;
	}
	public void setPostType(PostType postType) {
		this.postType = postType;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
