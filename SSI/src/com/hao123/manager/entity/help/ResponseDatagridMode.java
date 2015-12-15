package com.hao123.manager.entity.help;

import java.util.List;

/**
 * 封装easyui datagrid使用的的数据格式
 * 
 * @author lq
 * @time 2015-12-15 16:53:40
 *
 */
public class ResponseDatagridMode {
	
	/**
	 * 总条数
	 */
	private Long total;
	
	/**
	 * 数据集合
	 */
	private List<?> rows;

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

}
