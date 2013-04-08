package tripServerMain;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import tripBean.TripInfo;
import tripUtil.SearchTrip;

/*
 *对客户端传送过来的数据进行trip查询 
 * */
public class MinaServer {
	public static ResourceBundle resourceBundle;
	static {
        resourceBundle = ResourceBundle.getBundle("sysParam");
    }
	
	public static void main(String[] args) {
		IoAcceptor acceptor = new NioSocketAcceptor(4);
		//acceptor.setBacklog(Integer.parseInt(resourceBundle.getString("maxBackLog")));
		acceptor.getFilterChain().addLast("mychin",
				new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
		
//		acceptor.getFilterChain().addLast("ExecutorThread",new  ExecutorFilter(Executors.newCachedThreadPool()));
		
//		acceptor.getSessionConfig().setIdleTime( IdleStatus.BOTH_IDLE,10);//设置10秒后 进入空闲状态
		
		acceptor.setHandler(new IoHandlerAdapter() {
			@Override
			public void sessionCreated(IoSession session) throws Exception {
				
				System.out.println("session created");
				
			}

			@Override
			public void sessionOpened(IoSession session) throws Exception {

			}

			@Override
			public void sessionClosed(IoSession session) throws Exception {
				//System.out.println("session closed");
			}

			@Override
			public void sessionIdle(IoSession session, IdleStatus status)
					throws Exception {
				
			}

			@Override
			public void exceptionCaught(IoSession session, Throwable cause)
					throws Exception {
				
				System.out.println("cuowu yuany "+cause);
//				session.close(true);
			}

			//主要处理业务的类，接收到客户端传送的
			@Override
			public void messageReceived(IoSession session, Object message){
				
//				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
				long start=System.currentTimeMillis();
				TripInfo tif=(TripInfo) message;
				SearchTrip st=new SearchTrip();
				//查询出trip结果
				List<Object> list=st.search(tif.getTripSql(),tif.getSelectNum(), "tripBean.SearchInfo");
				tif.setList(list); 
				if(list!=null){
					tif.setReturnNum(String.valueOf(list.size()));
				}
				
				session.write(tif);
				long end=System.currentTimeMillis();
				System.out.println("一共用时："+(end-start));
			}

			@Override
			public void messageSent(IoSession session, Object message)
					throws Exception {
				
				
			}
		});

		
		try {
			acceptor.bind(new InetSocketAddress(Integer.parseInt(resourceBundle.getString("serverPort"))));
			System.out.println("The TripServer has working!!");
		} catch (IOException ex) {
			System.out.println("服务器抛出异常，错误原因:"+ex.toString());
		}
	}

}
