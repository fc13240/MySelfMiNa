package tripBean;

import java.io.Serializable;
import java.util.List;

/*
 *ͨ��mina����trip��ѯʱ�򷵻ص�ʵ���� 
 * */
public class TripInfo implements Serializable{
	
	private String selectNum="0";//��ѯ���ص���������
	private List<Object> list;//��ѯ���������ݼ���
	private String returnNum;//��ѯ��������������
	private String tripSql="";//trip�Ĳ�ѯ��䣬ֻ�ڿͻ�����ֵ���ڷ�������ֻ�Ǹ�����գ�Ȼ����в�ѯ
	
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
