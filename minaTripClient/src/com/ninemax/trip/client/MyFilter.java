package com.ninemax.trip.client;

import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.filterchain.IoFilterChain;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.WriteRequest;

/*
 *自定义过滤器 
 * */
public class MyFilter implements IoFilter {

	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void exceptionCaught(NextFilter nextfilter, IoSession iosession,
			Throwable throwable) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void filterClose(NextFilter nextfilter, IoSession iosession)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void filterWrite(NextFilter nextfilter, IoSession iosession,
			WriteRequest writerequest) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void init() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void messageReceived(NextFilter nextfilter, IoSession iosession,
			Object obj) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void messageSent(NextFilter nextfilter, IoSession iosession,
			WriteRequest writerequest) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void onPostAdd(IoFilterChain iofilterchain, String s,
			NextFilter nextfilter) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void onPostRemove(IoFilterChain iofilterchain, String s,
			NextFilter nextfilter) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void onPreAdd(IoFilterChain iofilterchain, String s,
			NextFilter nextfilter) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void onPreRemove(IoFilterChain iofilterchain, String s,
			NextFilter nextfilter) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void sessionClosed(NextFilter nextfilter, IoSession iosession)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void sessionCreated(NextFilter nextfilter, IoSession iosession)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void sessionIdle(NextFilter nextfilter, IoSession iosession,
			IdleStatus idlestatus) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void sessionOpened(NextFilter nextfilter, IoSession iosession)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
