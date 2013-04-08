package tripBean;

import java.io.Serializable;
import java.util.List;

/*
 *通过mina进行trip查询时候返回的实体类 
 * */
public class TripInfo implements Serializable{
	
	private String selectNum="0";//查询返回的数据条数
	private List<Object> list;//查询出来的数据集合
	private String returnNum;//查询出来的数据条数
	private String tripSql="";//trip的查询语句，只在客户端有值，在服务器端只是负责接收，然后进行查询
	
	public String getSelectNum() {
		return selectNum;
	}
	public void setSelectNum(String selectNum) {
		this.selectNum = selectNum;
	}
	
	public List<Object> getList() {
		return list;
	}
	public void setList(List<Object> list) {
		this.list = list;
	}
	public String getReturnNum() {
		return returnNum;
	}
	public void setReturnNum(String returnNum) {
		this.returnNum = returnNum;
	}
	public String getTripSql() {
		return tripSql;
	}
	public void setTripSql(String tripSql) {
		this.tripSql = tripSql;
	}
	
	
}
