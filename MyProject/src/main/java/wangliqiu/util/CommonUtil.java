package wangliqiu.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonUtil {
	private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

	/**
	 * 统计
	 * 
	 * @see 去重是无序的
	 */
	public static Collection<String> statistics(Collection<String> collection) {
		Collection<String> listDone = new ArrayList<String>();
		HashSet<String> set = new HashSet<String>(collection);// 去掉重复元素
		listDone.addAll(set);

		// 统计
		for (String str : listDone) {
			int num = Collections.frequency(collection, str);
			logger.info(str + ":  " + num);
		}

		return listDone;
	}

}
