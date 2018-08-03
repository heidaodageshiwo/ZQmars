/**  
* @Title:  Pager.java
* @Package com.colldown.common
* @Description: TODO(用一句话描述该文件做什么)
* @author Wayne
* @date  2015-5-10 下午3:50:43
* @version V1.0  
* Update Logs:
* ****************************************************
* Name:
* Date:
* Description:
******************************************************
*/
package com.iekun.ef.dao;

import java.util.List;

/**
 * @ClassName: Pager
 * @Description: TODO(分页封装类)
 * @author Wayne
 * @date 2015-5-10 下午3:50:43
 *
 */
public class DataTablePager {
	private String sEcho;				//无实际意义，原值返回
    private long iTotalRecords;			//总记录条数
    private long iTotalDisplayRecords;	//显示总记录条数
    private int iDisplayStart;			//分页属性 起始页
    private int iDisplayLength;			//分页属性  每页显示记录条数
    private List dataResult;			//结果集
    
    /***封装datatables参数**/
    private int iSortCol;				//排序列 ： 0,1,2……
    private String sSortDir;			//正序反序  asc,desc
    private String sColumns;			//表格列名顺序
    
	public String getsEcho() {
		return sEcho;
	}
	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}

	public long getiTotalRecords() {
		return iTotalRecords;
	}
	public void setiTotalRecords(long iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}
	public long getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}
	public void setiTotalDisplayRecords(long iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}
	public int getiDisplayStart() {
		return iDisplayStart;
	}
	public void setiDisplayStart(int iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}
	public int getiDisplayLength() {
		return iDisplayLength;
	}
	public void setiDisplayLength(int iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
	}
	public List getDataResult() {
		return dataResult;
	}
	public void setDataResult(List dataResult) {
		this.dataResult = dataResult;
	}
	public int getiSortCol() {
		return iSortCol;
	}
	public void setiSortCol(int iSortCol) {
		this.iSortCol = iSortCol;
	}
	public String getsSortDir() {
		return sSortDir;
	}
	public void setsSortDir(String sSortDir) {
		this.sSortDir = sSortDir;
	}
	public String getsColumns() {
		return sColumns;
	}
	public void setsColumns(String sColumns) {
		this.sColumns = sColumns;
	}

    
}
