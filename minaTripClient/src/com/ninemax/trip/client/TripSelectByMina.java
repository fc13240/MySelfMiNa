package com.ninemax.trip.client;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import tripBean.TripInfo;




/*
 *使用mina进行trip语句查询 
 * */
public class TripSelectByMina {
	private static TripInfo returnTrip;
	private static String hostAddress="localhost";//服务端地址
	private static int hostPort=8888;//服务端端口号
	private SocketConnector  connector=null;
	//连接服务端，进行trip数据查询
	public void DoMina(final TripInfo tif){
		connector= new NioSocketConnector();  
//	        connector.setConnectTimeoutMillis(2 * 1000);  //设置为10秒失效
		
	        connector.getFilterChain().addLast("mychin", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
	        
	        connector.getFilterChain().addLast("ExecutorThread", new ExecutorFilter(Executors.newCachedThreadPool()));
	        
	        connector.setHandler(new IoHandlerAdapter() {  
	              
	        	//建立同服务端的连接
	            @Override  
	            public void sessionCreated(IoSession session) throws Exception {  
	            }  
	  
	            //当连接建立后，传送过实体对象到服务端进行查询
	            @Override  
	            public void sessionOpened(IoSession session) throws Exception { 
//	            	TripInfo ti=tif;
	                session.write(tif);  
	                System.out.println("客户端发送过去了！！ 查询语句是："+tif.getTripSql());
	            }  
	  
	            @Override  
	           // 当客户端关闭对
	            public void sessionClosed(IoSession session) throws Exception {  
	            }  
	  
	            @Override  
	            //当客户端空闲的时候
	            public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
	            	session.close(true);
//	                connector.dispose();
	            }  
	            
	            @Override
	            //当抛出异常的时候，关闭会话
	            public void exceptionCaught(IoSession session, Throwable cause) throws Exception {  
	                System.out.println("client Exception cause is :"+cause);
	            	session.close(true);  
			    	connector.dispose();
	            }  
	  
	            //接收从服务端处理后的消息
	            @Override  
	            public void messageReceived(IoSession session, Object message) throws Exception {
//	            	System.out.println("returnTrip:"+returnTrip.getList().size());
//	            	returnTrip=new TripInfo();
	            	returnTrip=(TripInfo)message;
	            	System.out.println("return message is "+returnTrip);
	                session.close(true); //关闭本次会话
	                connector.dispose();
	            }  
	  
	            @Override  
	            public void messageSent(IoSession session, Object message) throws Exception {
//	            	System.out.println("发送消息成功");
	            }  
	        });  
			  try {  
		          ConnectFuture future = connector.connect(new InetSocketAddress(hostAddress, hostPort));
//		          future.awaitUninterruptibly();
		      } catch (RuntimeIoException e) {  
		    	  System.out.println("抛出异常，错误原因："+e.toString());
		    	  connector.dispose();
			        }
	}

	
	public TripInfo getReturn(){
//		while(returnTrip==null){
//			
//		}
//		System.out.println("返回的return是："+returnTrip.getList().size());
		
		return returnTrip;
	}
	
	public  TripInfo getReturnTrip() {
		return returnTrip;
	}

	public  void setReturnTrip(TripInfo returnTrip) {
		this.returnTrip = returnTrip;
	}
	
	
}
