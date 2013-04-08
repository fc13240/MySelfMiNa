package tripUtil;

import java.lang.reflect.Field;
import java.util.*;

import com.tietoenator.trip.jxp.TdbException;
import com.tietoenator.trip.jxp.data.TdbComponent;
import com.tietoenator.trip.jxp.data.TdbRecord;
import com.tietoenator.trip.jxp.data.TdbRecordSet;
import com.tietoenator.trip.jxp.session.TdbSession;

/*
 * Ϊ�ӿ��ṩ�Ĳ�ѯ����,ȡ�������ֶ�����
 * */
public class SearchTrip {
	
	private LinkedList<Object> reList=null;
	private static Class cl=null;
	private static Field[] fi=null;
	//�������Ϊ trip��ѯ��䣬���صĽ������(Ĭ��Ϊ100)����ŵ�ʵ���ࣨ�����У�
	public List<Object> search(String tripStr,String resNum,String className){
		TdbSession tSession=null;
		reList=new LinkedList<Object>();
		try {
			//�õ�Session
			tSession=NewTripPoolManage2.getSession();
			TdbRecordSet trs=new TdbRecordSet(tSession);
			trs.setDatabase(NewTripPoolManage2.resourceBundle.getString("tripDB"));
			trs.setQueryCCL(true);
			trs.setFrom(1);
			if(resNum!=null&&!"".equals(resNum)&&!"0".equals(resNum)){
				trs.setTo(Integer.parseInt(resNum));
			}else{
				trs.setTo(100);
			}
			
			/*
			 *�Ƿ�������� 
			 * 
			//�����ֶ�
			trs.setSortKeys("");
			//��������ֵ��Ӧ�ô��ڵ��ڸ��ݲ�ѯ����ѯ����������,����ԽС���ٶ�Խ��
			trs.setSortMax(1000);
			//�Ƿ���е��򣬲���ûЧ�� ����֪��ԭ��
//			trs.setSortRanked(false);
			*/
			
			TdbRecord tr=new TdbRecord(tSession);
			if(cl==null){
				System.out.println("������Class��ʼ��");
				cl=Class.forName(className.trim());
			}
			
//			System.out.println("������trip��ѯ");
			
			//����ȡ��SearchInfo �е���������
			if(fi==null){
				System.out.println("������Fields��ʼ��");
				fi=cl.getDeclaredFields();
			}
			
			for(int n=0,len=fi.length;n<len;n++){
				tr.addToTemplate(fi[n].getName());
			}
			
			trs.setRetrievalTemplate(tr);
			trs.setQuery(tripStr);
			if(tripStr!=null&&!"".equals(tripStr)){
				try{
					trs.get();
				}catch(Exception e){
					System.out.println("============û�в�ѯ����¼��============");
				}
			}
			//ȡ�����в�ѯ�������ݣ����뵽List��
			List<TdbRecord> list=trs.records();
			if(list!=null){
				for(TdbRecord tre:list){
					//���ݴ����Class���ִ���һ��Object,������Ų�ѯ������һ����¼��Ϣ
					Object si=cl.newInstance();
					TdbComponent tc=tre.getHead();
					
					for(int m=0,len=fi.length;m<len;m++){
						Field fe2=fi[m];
						fe2.set(si,tc.getField(fe2.getName()).toString());
					}
					reList.add(si);
				}
			}
			
			trs.clear();
		} catch (TdbException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}finally{
			try {
				 if(tSession != null){
					 tSession.logout(); 
					 tSession = null;
		            }
			} catch (TdbException e) {
				e.printStackTrace();
			}
           
		}
		return reList;
	} 
}
