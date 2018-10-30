package com.bbs.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.Entity;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.pagehelper.StringUtil;
import com.kun.bean.PageBean;
import com.kun.bean.QueryResult;
import com.kun.cfg.Configuration;
import com.kun.util.HqlHelper;





@SuppressWarnings({ "unchecked" })
@Transactional
public class BaseDaoImpl<T> implements BaseDao<T> {

	private static Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);
	
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;
	private Class<?> clazz;

	// 获取实现类类型clazz
	public BaseDaoImpl() {
		ParameterizedType parameterizedType = (ParameterizedType) this
				.getClass().getGenericSuperclass();
		Type[] types = parameterizedType.getActualTypeArguments();
		clazz = (Class<?>) types[0];
	}

	public void save(T entity) {
		getSession().save(entity);
	}

	public void update(T entity) {
		getSession().update(entity);
	}

	public void delete(Long id) {
		Object obj = getSession().get(clazz, id);
		getSession().delete(obj);
	}

	public T getById(Long id) {
		if (id == null) {
			return null;
		}
		return (T) getSession().get(clazz, id);
	}

	public List<T> getByIds(Long[] ids) {
		if (ids == null || ids.length == 0) {
			return Collections.EMPTY_LIST; // 返回空集合
		}

		return getSession().createQuery(
				"FROM " + clazz.getSimpleName() + " WHERE id IN(:ids)")
				.setParameterList("ids", ids).list();
	}

	public List<T> findAll() {
		return getSession().createQuery("FROM " + clazz.getSimpleName()).list();
	}

	public QueryResult<T> getPageBean(Class<T> entityClass, int firstIndex,
			int maxResult, String whereJpql, Object[] queryParams,
			LinkedHashMap<String, String> orderBy) {

		QueryResult<T> qr = new QueryResult<T>();
		String entityName = getEntityName(entityClass);
		Query query = getSession().createQuery(
				"from "
						+ entityName
						+ " o "
						+ (whereJpql == null || "".equals(whereJpql) ? ""
								: "where " + whereJpql) + buidOrderBy(orderBy));
		setQueryParams(query, queryParams);
		if (firstIndex != -1 && maxResult != -1) {
			query.setFirstResult(firstIndex);
			query.setMaxResults(maxResult);
		}
		// 设置集合
		qr.setResultList(query.list());

		// 如果没有分页的话，就没必要统计记录数
		if (firstIndex != -1 && maxResult != -1) {
			query = getSession().createQuery(
					"select count(o) from " + entityName + " o "
							+ (whereJpql == null ? "" : "where " + whereJpql));
			setQueryParams(query, queryParams);
			// 设置记录数
			qr.setTotalRecords((Long) query.uniqueResult());
		}
		return qr;
	}

	/**
	 * 设置where语句的查询参数
	 * 
	 * @param query
	 * @param queryParams
	 */
	protected void setQueryParams(Query query, Object[] queryParams) {
		if (queryParams != null && queryParams.length > 0) {
			for (int i = 0; i < queryParams.length; i++) {
				query.setParameter(i, queryParams[i]);
			}
		}
	}

	/**
	 * 组装order by语句
	 * 
	 * @param orderBy
	 * @return
	 */
	protected String buidOrderBy(LinkedHashMap<String, String> orderBy) {
		StringBuffer orderBySql = new StringBuffer();
		if (orderBy != null && orderBy.size() > 0) {
			orderBySql.append(" order by ");
			for (String key : orderBy.keySet()) {
				orderBySql.append("o.").append(key).append(" ").append(
						orderBy.get(key)).append(",");
			}
			orderBySql.deleteCharAt(orderBySql.length() - 1);
		}
		return orderBySql.toString();
	}

	/**
	 * 获取实体名称
	 * 
	 * @param <T>
	 * @param entityClass
	 * @return
	 */
	protected String getEntityName(Class<T> entityClass) {
		String entityName = entityClass.getSimpleName();
		Entity entity = entityClass.getAnnotation(Entity.class);
		if (entity.name() != null && !"".equals(entity.name())) {
			entityName = entity.name();
		}
		return entityName;
	}

	// 公共的查询分页信息的方法
	@SuppressWarnings("rawtypes")
	public PageBean getPageBean(int pageNum, String queryListHQL,
			Object[] parameters) {

		int pageSize = Configuration.getPageSize();

		// 查询本页的数据列表
		Query listQuery = getSession().createQuery(queryListHQL);
		if (parameters != null && parameters.length > 0) { // 设置参数
			for (int i = 0; i < parameters.length; i++) {
				listQuery.setParameter(i, parameters[i]);
			}
		}
		listQuery.setFirstResult((pageNum - 1) * pageSize);
		listQuery.setMaxResults(pageSize);
		List list = listQuery.list(); // 执行查询

		// 查询总记录数
		Query countQuery = getSession().createQuery(
				"SELECT COUNT(*) " + queryListHQL);
		if (parameters != null && parameters.length > 0) { // 设置参数
			for (int i = 0; i < parameters.length; i++) {
				countQuery.setParameter(i, parameters[i]);
			}
		}
		Long count = (Long) countQuery.uniqueResult(); // 执行查询

		return new PageBean(pageNum, pageSize, list, count.intValue());
	}

	// 最终版
	@SuppressWarnings("rawtypes")
	public PageBean getPageBean(int pageNum, HqlHelper hqlHelper) {

		int pageSize = Configuration.getPageSize();
		List<Object> parameters = hqlHelper.getParameters();

		// 查询本页的数据列表
		Query listQuery = getSession().createQuery(hqlHelper.getQueryListHql());
		if (parameters != null && parameters.size() > 0) { // 设置参数
			for (int i = 0; i < parameters.size(); i++) {
				listQuery.setParameter(i, parameters.get(i));
			}
		}
		listQuery.setFirstResult((pageNum - 1) * pageSize);
		listQuery.setMaxResults(pageSize);
		List list = listQuery.list(); // 执行查询
		logger.info("\r\n HqlHelper查询结果：" + list);
		// 查询总记录数
		Query countQuery = getSession().createQuery(
				hqlHelper.getQueryCountHql());
		if (parameters != null && parameters.size() > 0) { // 设置参数
			for (int i = 0; i < parameters.size(); i++) {
				countQuery.setParameter(i, parameters.get(i));
			}
		}
		Long count = 0l;
//		if(!hqlHelper.isJoin()) {
//			count = (Long) countQuery.uniqueResult(); // 执行查询
//		}
		if(!StringUtil.isEmpty(hqlHelper.getQueryCountHql()) && hqlHelper.getQueryCountHql().contains("GROUP BY")) {
			count = (long) countQuery.list().size(); // 执行查询
		} else {
			count = (Long) countQuery.uniqueResult(); // 执行查询
		}
		//Long count = (list == null ? 0l : list.size());

		return new PageBean(pageNum, pageSize, list, count.intValue());
	}

	/**
	 * 动态参数条件查询
	 * @param hqlHelper
	 * @param firstResult 如果不需要分页则 -1
	 * @param maxResult   如果不需要分页则 -1
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<T> getBeanList(HqlHelper hqlHelper,Integer firstResult,Integer maxResult) {

		List<Object> parameters = hqlHelper.getParameters();

		// 数据列表
		Query listQuery = getSession().createQuery(hqlHelper.getQueryListHql());
		if (parameters != null && parameters.size() > 0) { // 设置参数
			for (int i = 0; i < parameters.size(); i++) {
				listQuery.setParameter(i, parameters.get(i));
			}
		}
		if (firstResult != -1 && maxResult != -1) {
			listQuery.setFirstResult(firstResult);
			listQuery.setMaxResults(maxResult);
		}
		
		List list = listQuery.list(); // 执行查询


		return list;
	}

	/**
	 * 获取当前可用的Session
	 * 
	 * @return
	 */
	protected Session getSession() {
		//return sessionFactory.openSession();
		return sessionFactory.getCurrentSession();
	}

	public void insertBatch(List<T> entityClasses) {
		for (int i = 0; i < entityClasses.size(); i++) {
			getSession().save(entityClasses.get(i));
			if (i % 20 == 0 ) { //20, same as the JDBC batch size
		        //flush a batch of inserts and release memory:
		        getSession().flush();
		        getSession().clear();
		    }
		}
	}

	public void updateBatch(List<T> entityClasses) {
		for (int i = 0; i < entityClasses.size(); i++) {
			getSession().update(entityClasses.get(i));
			if (i % 20 == 0) {
				//flush a batch of inserts and release memory:
		        getSession().flush();
		        getSession().clear();
			}
		}
	}

}
