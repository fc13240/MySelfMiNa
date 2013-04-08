package com.ninemax.trip.client;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import tripBean.TripInfo;

public class TripHandler implements IoHandler {
	private TripInfo returnTrip=null;//返回结果
	private TripInfo tif=null;//传入参数
	public TripHandler(TripInfo tif){
		this.tif=tif;
	}
	public TripHandler( ){
			super();
		}
	
	//建立同服务端的连接
    public void sessionCreated(IoSession session) throws Exception {  
    }  

    //当连接建立后，传送过实体对象到服务端进行查询
    public void sessionOpened(IoSession session) throws Exception { 
//    	TripInfo ti=tif;
        session.write(tif);  
//        System.out.println("客户端发送过去了！！ 查询语句是："+tif.getTripSql());
    }  

      
   // 当客户端关闭对
    public void sessionClosed(IoSession session) throws Exception {  
    }  

      
    //当客户端空闲的时候
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
    	session.close(true);
    }  
    
    
    //当抛出异常的时候，关闭会话
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {  
//        System.out.println("client Exception cause is :"+cause);
    	session.close(true);  
    }  

    //接收从服务端处理后的消息
      
    public void messageReceived(IoSession session, Object message) throws Exception {
//    	System.out.println("returnTrip:"+returnTrip.getList().size());
//    	returnTrip=new TripInfo();
    	returnTrip=(TripInfo)message;
//    	System.out.println("return message is "+returnTrip);
        session.close(true); //关闭本次会话
//        connector.dispose();
    }  

      
    public void messageSent(IoSession session, Object message) throws Exception {
//    	System.out.println("发送消息成功");
    }
	public TripInfo getReturnTrip() {
		return returnTrip;
	}  
}
