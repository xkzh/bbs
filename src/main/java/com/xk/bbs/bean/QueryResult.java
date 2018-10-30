package com.xk.bbs.bean;



import java.util.List;

public class QueryResult<T> {

	private List<T> resultList;
	private Long totalRecords;
	
	public List<T> getResultList() {
		return resultList;
	}
	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}
	public Long getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(Long totalRecords) {
		this.totalRecords = totalRecords;
	}
	
}
