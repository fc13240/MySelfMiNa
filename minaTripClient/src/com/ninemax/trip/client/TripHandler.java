package com.ninemax.trip.client;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import tripBean.TripInfo;

public class TripHandler implements IoHandler {
	private TripInfo returnTrip=null;//���ؽ��
	private TripInfo tif=null;//�������
	public TripHandler(TripInfo tif){
		this.tif=tif;
	}
	public TripHandler( ){
			super();
		}
	
	//����ͬ����˵�����
    public void sessionCreated(IoSession session) throws Exception {  
    }  

    //�����ӽ����󣬴��͹�ʵ����󵽷���˽��в�ѯ
    public void sessionOpened(IoSession session) throws Exception { 
//    	TripInfo ti=tif;
        session.write(tif);  
//        System.out.println("�ͻ��˷��͹�ȥ�ˣ��� ��ѯ����ǣ�"+tif.getTripSql());
    }  

      
   // ���ͻ��˹رն�
    public void sessionClosed(IoSession session) throws Exception {  
    }  

      
    //���ͻ��˿��е�ʱ��
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
    	session.close(true);
    }  
    
    
    //���׳��쳣��ʱ�򣬹رջỰ
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {  
//        System.out.println("client Exception cause is :"+cause);
    	session.close(true);  
    }  

    //���մӷ���˴�������Ϣ
      
    public void messageReceived(IoSession session, Object message) throws Exception {
//    	System.out.println("returnTrip:"+returnTrip.getList().size());
//    	returnTrip=new TripInfo();
    	returnTrip=(TripInfo)message;
//    	System.out.println("return message is "+returnTrip);
        session.close(true); //�رձ��λỰ
//        connector.dispose();
    }  

      
    public void messageSent(IoSession session, Object message) throws Exception {
//    	System.out.println("������Ϣ�ɹ�");
    }
	public TripInfo getReturnTrip() {
		return returnTrip;
	}  
}
