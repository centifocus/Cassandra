package wangliqiu.test.util;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.VisitorSupport;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import visitor.Data;
import visitor.Visitor;
import wangliqiu.util.XmlParser;

public class TestOfXmlParser {
	protected static Logger logger = Logger.getLogger(TestOfXmlParser.class);
	private static final String xmlPath = System.getProperty("user.dir") + "/config/config.xml";

	@Test
	public void hashmap() throws Exception {
		HashMap<String, Object> per = new HashMap<>();
		for (Entry<String, Object> entry : per.entrySet()) {
			System.out.println(entry.getKey() + "  " + entry.getValue());
		}

	}

	@Test
	public void addElements() throws Exception {
		// XmlParser xmlParser = new XmlParser(new File(System.getProperty("user.dir") +
		// "/config/config.xml"));
		//
		// System.out.println(xmlParser.selectSingNode("//prop", "").getText());

		XmlParser xmlParser = new XmlParser();
		Element element = xmlParser.addRootElement("wangliqiu", "wangviviying!!!");
		HashMap<String, String> map = new HashMap<>();
		map.put("newname", "centifocus");
		map.put("age", "25");
		xmlParser.addElement(element, "person", "he is a man", map, "no comments");

		xmlParser.write("F:\\sss.xml", null);

		// Visitor模式遍历
		xmlParser.getDocument().accept(new VisitorSupport() {

			@Override
			public void visit(Element element) {
				System.out.println("type:" + element.getName());
			}

			@Override
			public void visit(Attribute attr) {
				System.out.println("attribute name:" + attr.getName() + "   attribute value:" + attr.getValue());
			}

		});

	}

	@Test
	public void read() throws MalformedURLException, DocumentException {
		Document document = new SAXReader().read(new File(xmlPath));
		// 获取整个报文RootElement
		Element RootElement = document.getRootElement();

		// elementIterator()只遍历子节点，不遍历子孙节点
		for (Iterator<Element> i = RootElement.elementIterator(); i.hasNext();) {
			System.out.println(i.next().attributeValue("name"));
		}
		// 获取version的节点
		for (Iterator<Element> i = RootElement.elementIterator("version"); i.hasNext();) {
			System.out.println(i.next().getText());
		}

		// List<Node> elementList = document.selectNodes("//mq[@name= 'REQ']");//选择具体节点
		List<Node> elementList = document.selectNodes("//prop[contains(@type, 'r') or contains(@type, 'rw')]");// /head/mq/prop");
		Iterator<Node> elementIter = elementList.iterator();
		while (elementIter.hasNext()) {
			Node node = elementIter.next();
			node.setName("wangliqiu");
			System.out.println(node.asXML());// node.getName());
			System.out.println(node.getParent().asXML());

			node.detach();// 移除节点
		}

		System.out.println(document.asXML());

	}

}
