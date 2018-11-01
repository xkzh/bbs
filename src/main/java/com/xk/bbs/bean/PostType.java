package com.xk.bbs.bean;

import com.google.gson.Gson;

import javax.persistence.*;

@Table(name = "t_posttype")
@Entity
public class PostType {
	private int id;
	private String name;
	private String color;
	private String alias;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
