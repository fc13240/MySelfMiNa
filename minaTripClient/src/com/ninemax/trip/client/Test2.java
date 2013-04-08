package com.ninemax.trip.client;

import tripBean.TripInfo;






public class Test2 {
	public static void main(String[] args) {
		for(int i=0;i<10;i++){
			NewThread nt = new NewThread();
			nt.start();
		}
		
	}
	
	
}
class NewThread extends Thread {
	private int a;
	public NewThread(int _num){
		a = _num;
	}
	public NewThread(){
		
	}
	public void run() {
		boolean b=true;
		
		try{
			TripSelectByMina tsb=new TripSelectByMina();
			TripInfo tif=new TripInfo();//存放trip查询数据的实体bean
			tif.setTripSql("Find   JGMC=齐市碾子山区 ");
			tif.setSelectNum("100");
			tsb.DoMina(tif);//进行trip查询
			while(true){
				if(tif!=null&&tif.getList()!=null){
					System.out.println("结果是："+tif.getList().size());
					return;
				}
				tif =tsb.getReturn();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
