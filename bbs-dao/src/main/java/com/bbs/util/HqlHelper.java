package com.bbs.util;
import com.bbs.dao.BaseDao;
import com.xk.bbs.bean.PageBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;



/**
 * 用于辅助拼接生成HQL的工具类
 * 
 * @author lc
 * 
 */
public class HqlHelper {

	private String fromClause; // From子句，必须
	private String whereClause = ""; // Where子句，可选
	private String orderByClause = ""; // OrderBy子句，可选
	private String groupByClause = ""; // GroupBy子句，可选
	private boolean join = false;
	private static Logger logger = LoggerFactory.getLogger(HqlHelper.class);
//	private static final Logger logger = Logger.getLogger(Logger.class);
	
	private List<Object> parameters = new ArrayList<Object>(); // 参数列表

	/**
	 * 生成From子句，默认的别名为'o'
	 * 
	 * @param clazz
	 */
	@SuppressWarnings("rawtypes")
	public HqlHelper(Class clazz) {
		this.fromClause = "FROM " + clazz.getSimpleName() + " o";
	}

	/**
	 * 生成From子句，使用指定的别。'
	 * 
	 * @param clazz
	 * @param alias
	 *            别名
	 */
	@SuppressWarnings("rawtypes")
	public HqlHelper(Class clazz, String alias) {
		this.fromClause = "FROM " + clazz.getSimpleName() + " " + alias;
	}

	/**
	 * 拼接Where子句
	 * 
	 * @param condition
	 * @param params
	 */
	public HqlHelper addCondition(String condition, Object... params) {
		// 拼接
		if (whereClause.length() == 0) {
			whereClause = " WHERE " + condition;
		} else {
			whereClause += " AND " + condition;
		}

		// 保存参数
		if (params != null && params.length > 0) {
			for (Object obj : params) {
				parameters.add(obj);
			}
		}

		return this;
	}

	/**
	 * 如果第1个参数为true，则拼接Where子句
	 * 
	 * @param append
	 * @param condition
	 * @param params
	 */
	public HqlHelper addCondition(boolean append, String condition, Object... params) {
		if (append) {
			addCondition(condition, params);
		}
		return this;
	}

	/**
	 * 拼接OrderBy子句
	 * 
	 * @param propertyName
	 *            属性名
	 * @param isAsc
	 *            true表示升序，false表示降序
	 */
	public HqlHelper addOrder(String propertyName, boolean isAsc) {
		if (orderByClause.length() == 0) {
			orderByClause = " ORDER BY " + propertyName + (isAsc ? " ASC" : " DESC");
		} else {
			orderByClause += ", " + propertyName + (isAsc ? " ASC" : " DESC");
		}
		return this;
	}
	
	/**
	 * 拼接GroupBy子句
	 * 
	 * @param propertyName
	 *            属性名
	 */
	public HqlHelper addGroupBy(String propertyName) {
		if (groupByClause.length() == 0) {
			groupByClause = " GROUP BY " + propertyName;
		} else {
			groupByClause += " " + propertyName;
		}
		return this;
	}

	/**
	 * 如果第1个参数为true，则拼接OrderBy子句
	 * 
	 * @param append
	 * @param propertyName
	 *            属性名
	 * @param isAsc
	 *            true表示升序，false表示降序
	 */
	public HqlHelper addOrder(boolean append, String propertyName, boolean isAsc) {
		if (append) {
			addOrder(propertyName, isAsc);
		}
		return this;
	}

	/**
	 * 获取生成的查询数据列表的HQL语句
	 * 
	 * @return
	 */
	public String getQueryListHql() {
		String hql = fromClause + whereClause + groupByClause + orderByClause;
		logger.info("****** HQL语句******** "+hql);
		return hql;
	}

	/**
	 * 获取生成的查询总记录数的HQL语句（没有OrderBy子句）
	 * 
	 * @return
	 */
	public String getQueryCountHql() {
		return "SELECT COUNT(*) " + fromClause + whereClause + groupByClause;
	}

	/**
	 * 获取参数列表，与HQL过滤条件中的'?'一一对应
	 * 
	 * @return
	 */
	public List<Object> getParameters() {
		return parameters;
	}

	/**
	 * 查询并准备分页信息（放到栈顶）
	 * 
	 * @param pageNum
	 * @param service
	 * @return
	 */
	public HqlHelper buildPageBeanForController(int pageNum, BaseDao<?> service, Model model) {
		
		PageBean<?> pageBean = service.getPageBean(pageNum, this);
		model.addAttribute("pageBean", pageBean);
		//回显页码
		model.addAttribute("currPage", pageNum);
		return this;
	}
	/**
	 * 动态参数条件查询（封装后统一使用）
	 * @param service
	 * @param firstResult
	 * @param maxResult
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getBeanList(BaseDao<?> service,Integer firstResult,Integer maxResult){
		
		return service.getBeanList(this, firstResult, maxResult);
	}

	public boolean isJoin() {
		return join;
	}
	
}
