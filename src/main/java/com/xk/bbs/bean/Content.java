package com.xk.bbs.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name="t_content")
@Entity
public class Content implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String url;
	private String contentJson;
	private Date created;
	private Date updated;
	private Integer del = 0;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getContentJson() {
		return contentJson;
	}
	public void setContentJson(String contentJson) {
		this.contentJson = contentJson;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	public Integer getDel() {
		return del;
	}
	public void setDel(Integer del) {
		this.del = del;
	}
	
	
}
