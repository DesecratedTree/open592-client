package jagex3.jagmisc;

import java.net.InetAddress;

public final class jagmisc {

	public static long nanoTime() {
		return System.nanoTime();
	}

	public static int ping(byte arg0, byte arg1, byte arg2, byte arg3, long arg4) throws Throwable {
		byte[] address = new byte[] { arg0, arg1, arg2, arg3 };
		InetAddress inetAddress = InetAddress.getByAddress(address);
		long start = System.currentTimeMillis();
		if (!inetAddress.isReachable((int) arg4)) {
			throw new Exception("-1");
		}
		return (int) (System.currentTimeMillis() - start);
	}

	public static boolean init() {
		return true;
	}

	public static void quit() {
	}

	public static long getAvailablePhysicalMemory() {
		return Runtime.getRuntime().freeMemory();
	}

	public static long getTotalPhysicalMemory() {
		return Runtime.getRuntime().maxMemory();
	}

	private jagmisc() {
	}
}
