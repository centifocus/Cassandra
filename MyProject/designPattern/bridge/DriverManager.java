package bridge;

public abstract class DriverManager {

	private Driver driver;
	
	public void DBdriver(Driver driver){
		
	}
	
	public void setDriver(Driver driver){
		this.driver = driver;
	}
	
	public Driver getDriver(){
		return this.driver;
	}
	
}
