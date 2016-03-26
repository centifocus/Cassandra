package wangliqiu.net;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Iterator;

public class IPInterface {

	public static void main(String[] args) throws SocketException, UnknownHostException {
		// 网络适配器NetworkInterface
		Enumeration<NetworkInterface> networkIter = NetworkInterface.getNetworkInterfaces();

		while (networkIter.hasMoreElements()) {
			NetworkInterface netInter = networkIter.nextElement();
			Enumeration<InetAddress> ipEnum = netInter.getInetAddresses();

			while (ipEnum.hasMoreElements()) {
				String ip = ipEnum.nextElement().getHostAddress();
				System.out.println(netInter.getName() + " : " + ip);
			}

		}
		System.out.println(InetAddress.getLocalHost().getHostAddress());// 本地ip
	}

}
