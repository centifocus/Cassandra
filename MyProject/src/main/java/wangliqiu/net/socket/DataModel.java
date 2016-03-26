package wangliqiu.net.socket;

import java.io.Serializable;

public class DataModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public String name;

	public DataModel(String name) {
		this.name = name;
	}

}
