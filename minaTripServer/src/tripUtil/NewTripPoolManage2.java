package tripUtil;

import java.util.ResourceBundle;

import com.tietoenator.trip.jxp.TdbException;
import com.tietoenator.trip.jxp.TdbLanguage;
import com.tietoenator.trip.jxp.pool.TdbConnectionPool;
import com.tietoenator.trip.jxp.pool.TdbTripNetConnectionPool;
import com.tietoenator.trip.jxp.session.TdbSession;

public class NewTripPoolManage2 {
	 private static TdbTripNetConnectionPool pool = null;
	    private static int acquireSessionTimes = 20;
	    private static long acquireSessionInterval = 60L;
	    public static ResourceBundle resourceBundle;

	    static {
	        resourceBundle = ResourceBundle.getBundle("sysParam");
	    }

	    private static synchronized TdbConnectionPool initPool() {
	        try {
	            if (pool == null) {

	                pool = new TdbTripNetConnectionPool(
	                        resourceBundle.getString("tripIP"), Integer.parseInt(resourceBundle.getString("nPortNo")),
	                        resourceBundle.getString("tripUserName"), resourceBundle.getString("tripPassword"),
	                        TdbLanguage.DefaultLanguage, 2);

	                pool.setLimit(Integer.parseInt(resourceBundle.getString("num")));
	            }

	            return pool;
	        } catch (TdbException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

	    public static TdbSession getSession() {
	        TdbSession session = acquireSession();

	        if (session == null) {
	            for (int i = 1; i < acquireSessionTimes; ++i) {
	                session = acquireSession();
	                if (session == null)
	                    try {
	                        Thread.sleep(acquireSessionInterval);
	                    } catch (InterruptedException e) {
	                        e.printStackTrace();

	                    }
	                else
	                    return session;

	            }
	        }

	        return session;
	    }

	    public static TdbSession acquireSession() {
	        if (pool == null) {
	            initPool();
	        }
	        try {

	            return pool.acquire();
	        } catch (TdbException e) {
	            if (e.getCode() == -5) {
	                Thread.yield();
	                e.printStackTrace();
	            } else {
	                e.printStackTrace();
	            }
	            return null;
	        }
	    }

	    public void close() {
	        pool.close();
	    }
}
