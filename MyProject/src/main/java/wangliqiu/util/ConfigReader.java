package wangliqiu.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class ConfigReader {
	public static final Logger logger = Logger.getLogger(ConfigReader.class);

	/**
	 * Properties类继承Hashtable
	 * 
	 * @see Properties.load()实际上就是调用Hashtable的put方法，所以要重写put方法
	 * @see 若List转Set要保证有序，则Set也要是有序Set
	 */
	static class OrderedProperties extends Properties {
		private static final long serialVersionUID = 1L;

		private final List<String> keys = new ArrayList<String>();

		/**
		 * 获取有序的PropertyNames
		 */
		public Set<String> stringPropertyNames() {
			return new LinkedHashSet<String>(keys);
		}

		public Object put(Object key, Object value) {
			keys.add((String) key);
			return super.put(key, value);
		}
	}

	public static <T> void readProperties(String[] paths, Class<T> demo) {
		for (String path : paths) {
			readProperties(path, demo);
		}
	}

	/**
	 * 
	 * @see 含有@的属性为list，@前面为list名。
	 */
	public static <T> void readProperties(String filePath, T instance) {
		logger.debug("readProperties: " + filePath);
		try {
			OrderedProperties properties = new OrderedProperties();
			properties.load(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
			Set<String> set = properties.stringPropertyNames();

			// T[] objects = modelClass.getEnumConstants();
			Field[] fields = instance.getClass().getDeclaredFields();
			for (Field field : fields) {
				// list赋值
				if (field.getType().equals(List.class)) {
					field.setAccessible(true);
					boolean flag = false;// 只当properties有List属性，才操作
					List<String> list = new ArrayList<String>();
					for (String str : set) {
						int loc = str.indexOf("@");
						if (loc > -1 && str.substring(0, loc).equalsIgnoreCase(field.getName())) {
							list.add(properties.getProperty(str));
							flag = true;

							logger.debug(str + "   [" + list.get(list.size() - 1) + "]");
						}
					}
					if (flag) {
						field.set(instance, list);
					}
				}

				// 普通属性赋值
				if (set.contains(field.getName())) {// 当model类的field数大于properties
					field.setAccessible(true); // 强行访问
					String value = properties.getProperty(field.getName());
					if (field.getType().equals(String.class)) {// 确保类型匹配
						field.set(instance, value);
					} else if (field.getType().equals(int.class)) {
						field.set(instance, Integer.parseInt(value));
					} else if (field.getType().equals(boolean.class)) {
						if ("1".equals(value.trim())) {
							field.set(instance, true);
						} else {
							field.set(instance, false);
						}
					}

					logger.debug(field.getName() + "   [" + field.get(instance) + "]");
				}
			}
		} catch (Exception e) {
			logger.error(filePath + "读取出错，系统退出运行.", e);
			System.exit(0);
		}
	}

	/**
	 * 将xml映射到多个modelClass的实例，并将该多个实例加入Collection coll
	 */
	public static <T> void readXml(String filePath, Class<T> modelClass, String xpath, Collection<T> coll) {
		try {
			Document document = new SAXReader().read(new File(filePath));
			List<Node> elements = document.selectNodes(xpath);
			for (Node element : elements) {
				Map<String, String> properties = new HashMap<>();
				Iterator<Element> nodes = ((Element) element).elementIterator();
				for (; nodes.hasNext();) {
					Element node = nodes.next();
					properties.put(node.attributeValue("name"), node.getText());
				}

				T instance = modelClass.newInstance();
				Field[] fields = modelClass.getDeclaredFields();
				for (Field field : fields) {
					if (properties.keySet().contains(field.getName())) {// 当model类的field数大于properties
						field.setAccessible(true);
						String value = properties.get(field.getName());
						if (field.getType().equals(String.class)) {// 确保类型匹配
							field.set(instance, value);
						} else {
							field.set(instance, Integer.parseInt(value));
						}

						logger.debug(field.getName() + "   [" + field.get(instance) + "]");
					}
				}

				coll.add(instance);
			}
		} catch (Exception e) {
			logger.error(filePath + "读取出错.", e);
			System.exit(0);
		}

	}

}
