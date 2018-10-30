package com.xk.bbs.service;


import com.xk.bbs.bean.Content;
import com.xk.bbs.dao.BaseDao;

public interface ContentService extends BaseDao<Content> {

	Content getByUrl(String url);

	void addRecord(String res, String url);

	void updateRecord(String res, Long recordId);

}
