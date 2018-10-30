package com.xk.bbs.dao;

import com.xk.bbs.bean.PageBean;
import com.xk.bbs.bean.QueryResult;
import com.xk.bbs.util.HqlHelper;

import java.util.LinkedHashMap;
import java.util.List;


public interface BaseDao<T> {

	/**
	 * 保存实体
	 * 
	 * @param entity
	 */
	void save(T entity);

	/**
	 * 删除实体
	 * 
	 * @param id
	 */
	void delete(Long id);

	/**
	 * 更新实体
	 * 
	 * @param entity
	 */
	void update(T entity);

	/**
	 * 查询实体
	 * 
	 * @param id
	 * @return
	 */
	T getById(Long id);

	/**
	 * 查询实体
	 * 
	 * @param ids
	 * @return
	 */
	List<T> getByIds(Long[] ids);

	/**
	 * 查询所有
	 * 
	 * @return
	 */
	List<T> findAll();
	/**
	 * 批量插入
	 * @param entityClasses
	 */
	void insertBatch(List<T> entityClasses);
	
	void updateBatch(List<T> entityClasses);

	/**
	 * 根据参数及排序查询分页数据
	 * 
	 * @param entityClass
	 *            实体类
	 * @param firstIndex
	 *            开始索引
	 * @param maxResult
	 *            需要获取的记录数
	 * @param whereJpql
	 *            条件查询语句
	 * @param queryParams
	 *            条件查询参数
	 * @param orderBy
	 *            排序语句
	 * @return
	 */
	@Deprecated
    QueryResult<T> getPageBean(Class<T> entityClass, int firstIndex,
                               int maxResult, String whereJpql, Object[] queryParams,
                               LinkedHashMap<String, String> orderBy);
	
	
	
	/**
	 * 公共的查询分页信息的方法
	 * 
	 * @param pageNum
	 * @param queryListHQL
	 *            查询数据列表的HQL语句，如果在前面加上“select count(*) ”就变成了查询总数量的HQL语句了
	 * 
	 * @param parameters
	 *            参数列表，顺序与HQL中的'?'的顺序一一对应。
	 * @return
	 */
	@Deprecated
    PageBean<T> getPageBean(int pageNum, String queryListHQL, Object[] parameters);

	/**
	 * 公共的查询分页信息的方法
	 * 
	 * @param pageNum
	 * @param hqlHelper
	 *            查询条件（HQL语句与参数列表）
	 * @return
	 */
	PageBean<T> getPageBean(int pageNum, HqlHelper hqlHelper);
	/**
	 * 动态参数条件查询
	 * @param hqlHelper
	 * @param firstResult 如果不需要分页则 -1
	 * @param maxResult   如果不需要分页则 -1
	 * @return
	 */
	public List<T> getBeanList(HqlHelper hqlHelper, Integer firstResult, Integer maxResult);
}
