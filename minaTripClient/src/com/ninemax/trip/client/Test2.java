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
			TripInfo tif=new TripInfo();//���trip��ѯ���ݵ�ʵ��bean
			tif.setTripSql("Find   JGMC=��������ɽ�� ");
			tif.setSelectNum("100");
			tsb.DoMina(tif);//����trip��ѯ
			while(true){
				if(tif!=null&&tif.getList()!=null){
					System.out.println("����ǣ�"+tif.getList().size());
					return;
				}
				tif =tsb.getReturn();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
