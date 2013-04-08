package tripUtil;

import java.lang.reflect.Field;
import java.util.*;

import com.tietoenator.trip.jxp.TdbException;
import com.tietoenator.trip.jxp.data.TdbComponent;
import com.tietoenator.trip.jxp.data.TdbRecord;
import com.tietoenator.trip.jxp.data.TdbRecordSet;
import com.tietoenator.trip.jxp.session.TdbSession;

/*
 * 为接口提供的查询方法,取出所有字段属性
 * */
public class SearchTrip {
	
	private LinkedList<Object> reList=null;
	private static Class cl=null;
	private static Field[] fi=null;
	//传入参数为 trip查询语句，返回的结果数量(默认为100)，存放的实体类（必须有）
	public List<Object> search(String tripStr,String resNum,String className){
		TdbSession tSession=null;
		reList=new LinkedList<Object>();
		try {
			//得到Session
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
			 *是否进行排序 
			 * 
			//排序字段
			trs.setSortKeys("");
			//排序的最大值，应该大于等于根据查询语句查询出来的条数,数量越小，速度越快
			trs.setSortMax(1000);
			//是否进行倒序，不过没效果 ，不知道原因
//			trs.setSortRanked(false);
			*/
			
			TdbRecord tr=new TdbRecord(tSession);
			if(cl==null){
				System.out.println("进入了Class初始化");
				cl=Class.forName(className.trim());
			}
			
//			System.out.println("进入了trip查询");
			
			//反射取出SearchInfo 中的所有属性
			if(fi==null){
				System.out.println("进入了Fields初始化");
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
					System.out.println("============没有查询到记录！============");
				}
			}
			//取出所有查询到的数据，放入到List中
			List<TdbRecord> list=trs.records();
			if(list!=null){
				for(TdbRecord tre:list){
					//根据传入的Class名字创建一个Object,用来存放查询出来的一条记录信息
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
