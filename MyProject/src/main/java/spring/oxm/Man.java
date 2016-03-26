package spring.oxm;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * JAXB序列化XML时 默认序列化getter和setter，且getter和setter必须成对出现才会被序列化
 * 属性名称，默认序列化出来的类和属性名称默认是首字母转换为小写，若需要控制属性名称需要在getter或setter上使用 @XmlElement(name="ClassAId")
 * 
 * 怎么样实现序列化时使用Field字段而不是使用setter和getter， 在要使用的类上面加上@XmlAccessorType(XmlAccessType.FIELD)注解，
 * 这样你可以精确的控制每个元素的名称，而不需要为每个属性去设置@XmlElement(name="")注解，当然也可以在Field上使用@XmlElement注解。
 * 
 * @ps 添加命名空间 使用@XmlRootElement(namespace="cn.lzrabbit")
 */

@XmlRootElement(name = "RootMan")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true, value = { "other" })
// ignoreUnknown只在反序列化起作用；value为要屏蔽的属性
public class Man {
	public String name;
	public boolean isMale;
	public int age;
	@XmlElementWrapper(name = "myhome") // 只能位于集合属性上
	@XmlElements(value = { @XmlElement(name = "Home") })
	public List<Home> homes;
	@XmlElementWrapper(name = "mycard")
	public List<Card> cards;
	public String other;

	// jaxb只能对静态内部类才能实例化
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class Card {
		public Card() {

		}

		public Card(String msg) {
			this.msg = msg;

		}

		public String msg;
	}

	public Man() {

	}

	public Man(String name, boolean isMale, int age, List<Home> homes) {
		this.name = name;
		this.isMale = isMale;
		this.age = age;
		this.homes = homes;
	}

}
