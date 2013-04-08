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
 *ʹ��mina����trip����ѯ 
 * */
public class TripSelectByMina {
	private static TripInfo returnTrip;
	private static String hostAddress="localhost";//����˵�ַ
	private static int hostPort=8888;//����˶˿ں�
	private SocketConnector  connector=null;
	//���ӷ���ˣ�����trip���ݲ�ѯ
	public void DoMina(final TripInfo tif){
		connector= new NioSocketConnector();  
//	        connector.setConnectTimeoutMillis(2 * 1000);  //����Ϊ10��ʧЧ
		
	        connector.getFilterChain().addLast("mychin", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
	        
	        connector.getFilterChain().addLast("ExecutorThread", new ExecutorFilter(Executors.newCachedThreadPool()));
	        
	        connector.setHandler(new IoHandlerAdapter() {  
	              
	        	//����ͬ����˵�����
	            @Override  
	            public void sessionCreated(IoSession session) throws Exception {  
	            }  
	  
	            //�����ӽ����󣬴��͹�ʵ����󵽷���˽��в�ѯ
	            @Override  
	            public void sessionOpened(IoSession session) throws Exception { 
//	            	TripInfo ti=tif;
	                session.write(tif);  
	                System.out.println("�ͻ��˷��͹�ȥ�ˣ��� ��ѯ����ǣ�"+tif.getTripSql());
	            }  
	  
	            @Override  
	           // ���ͻ��˹رն�
	            public void sessionClosed(IoSession session) throws Exception {  
	            }  
	  
	            @Override  
	            //���ͻ��˿��е�ʱ��
	            public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
	            	session.close(true);
//	                connector.dispose();
	            }  
	            
	            @Override
	            //���׳��쳣��ʱ�򣬹رջỰ
	            public void exceptionCaught(IoSession session, Throwable cause) throws Exception {  
	                System.out.println("client Exception cause is :"+cause);
	            	session.close(true);  
			    	connector.dispose();
	            }  
	  
	            //���մӷ���˴�������Ϣ
	            @Override  
	            public void messageReceived(IoSession session, Object message) throws Exception {
//	            	System.out.println("returnTrip:"+returnTrip.getList().size());
//	            	returnTrip=new TripInfo();
	            	returnTrip=(TripInfo)message;
	            	System.out.println("return message is "+returnTrip);
	                session.close(true); //�رձ��λỰ
	                connector.dispose();
	            }  
	  
	            @Override  
	            public void messageSent(IoSession session, Object message) throws Exception {
//	            	System.out.println("������Ϣ�ɹ�");
	            }  
	        });  
			  try {  
		          ConnectFuture future = connector.connect(new InetSocketAddress(hostAddress, hostPort));
//		          future.awaitUninterruptibly();
		      } catch (RuntimeIoException e) {  
		    	  System.out.println("�׳��쳣������ԭ��"+e.toString());
		    	  connector.dispose();
			        }
	}

	
	public TripInfo getReturn(){
//		while(returnTrip==null){
//			
//		}
//		System.out.println("���ص�return�ǣ�"+returnTrip.getList().size());
		
		return returnTrip;
	}
	
	public  TripInfo getReturnTrip() {
		return returnTrip;
	}

	public  void setReturnTrip(TripInfo returnTrip) {
		this.returnTrip = returnTrip;
	}
	
	
}
