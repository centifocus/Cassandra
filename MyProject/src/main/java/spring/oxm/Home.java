package spring.oxm;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Home")
public class Home {

	public String address;

	public Home() {

	}

	public Home(String address) {
		this.address = address;
	}

}
