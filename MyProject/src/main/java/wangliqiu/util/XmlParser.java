package wangliqiu.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.XPath;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * xpath表达式 - “/”：表示选择根节点 - “//”：表示选择任意位置的某个节点 - “@”： 表示选择某个属性
 * 
 * @see Node是Element的父接口
 *
 */
public class XmlParser {

	private Document document;

	public XmlParser() {
		document = DocumentHelper.createDocument();
	}

	public XmlParser(String text) throws DocumentException {
		document = DocumentHelper.parseText(text);
	}

	public XmlParser(File file) throws DocumentException {
		document = new SAXReader().read(file);
	}

	public Document getDocument() {
		return document;
	}

	/**
	 * 添加根元素
	 */
	public Element addRootElement(String tag, String comment) {
		Element element = document.addElement(tag);
		element.addComment(comment);// 加注释

		return element;
	}

	/**
	 * 在ele中添加元素
	 */
	public Element addElement(Element ele, String tag, String text, Map<String, String> attribute, String comment) {
		Element element = ele.addElement(tag);
		if (attribute != null) {
			for (Entry<String, String> entry : attribute.entrySet()) {
				element.addAttribute(entry.getKey(), entry.getValue());
			}
		}
		element.addText(text).addComment(comment);

		return element;
	}

	/**
	 * 根据命名空间解析
	 */
	public Node selectSingleNode(String xpath, Map<String, String> xmlMap) {

		// Map<String, String> xmlMap = new HashMap<String, String>();
		// xmlMap.put("SOAP-ENV", "http://schemas.xmlsoap.org/soap/envelope/");
		// xmlMap.put("m", "http://www.shtel.com.cn/csb/v2/");
		XPath xPath = document.createXPath("//SOAP-ENV:Envelope//SOAP-ENV:Body//m:CSBThroughCallResponse//m:Data");
		xPath.setNamespaceURIs(xmlMap);

		Node node = xPath.selectSingleNode(document);// selectSingleNode只针对最基本的tag（tag里不包含其他节点）

		return node; // node.getText()
	}

	/**
	 * 将本体document写到filePath
	 */
	public void write(String filePath) throws IOException, DocumentException {
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		XMLWriter writer = new XMLWriter(new FileOutputStream(filePath), format);
		writer.write(document);
		writer.close();
	}

	/**
	 * 输出text到filePath
	 */
	public void write(String filePath, String text) throws IOException, DocumentException {
		OutputFormat format = OutputFormat.createPrettyPrint();// 缩进格式
		format.setEncoding("UTF-8");
		XMLWriter writer = new XMLWriter(new FileOutputStream(filePath), format);
		Document document = DocumentHelper.parseText(text);
		writer.write(document);
		writer.close();
	}

}
