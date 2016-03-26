package wangliqiu.util;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class NetUtil {
	public static final Logger logger = Logger.getLogger(NetUtil.class);

	/**
	 * 接口请求
	 */
	public static String interfaceRequest(String postUrl, String reqStr) {
		HttpPost post = new HttpPost(postUrl);
		post.setEntity(new StringEntity(reqStr, "UTF-8"));
		String resultInfo = "";
		try (CloseableHttpClient httpclient = HttpClients.createDefault();) {

			try (CloseableHttpResponse rsp = httpclient.execute(post);) {
				resultInfo = EntityUtils.toString(rsp.getEntity(), "UTF-8");
			}
		} catch (IOException e) {
			logger.error(e);
		}

		return resultInfo;
	}

}
