package com.xk.bbs.service.impl;

import com.xk.bbs.bean.Content;
import com.xk.bbs.dao.BaseDaoImpl;
import com.xk.bbs.service.ContentService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;


@Service
@Transactional
public class ContentServiceImpl extends BaseDaoImpl<Content> implements ContentService {

	public Content getByUrl(String url) {

		return (Content) getSession().createQuery("FROM Content WHERE url = ?0 and del = 0 order by id desc").setParameter(0, url).setFirstResult(0).setMaxResults(1).uniqueResult();
	}

	public void addRecord(String res, String url) {

		Date date = new Date();
		
		Content content = new Content();
		content.setContentJson(res);
		content.setCreated(date);
		content.setUpdated(date);
		content.setDel(0);
		content.setUrl(url);
		
		this.save(content);
	}

	public void updateRecord(String res, Long recordId) {
		Content content = this.getById(recordId);
		content.setContentJson(res);
		content.setUpdated(new Date());
		
		this.update(content);
	}


}
