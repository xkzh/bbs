package com.xk.bbs.bean;

import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "t_comment")
@Entity
public class Comment implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String content;
	private String createtime;
	private boolean enable;
	private int post_id;

	@ManyToOne // 一个用户可以发多条评论
	@JoinColumn(name = "user_id")
	private User user;

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public int getPost_id() {
		return post_id;
	}

	public void setPost_id(int post_id) {
		this.post_id = post_id;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
