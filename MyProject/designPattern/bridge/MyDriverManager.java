package bridge;


public class MyDriverManager extends DriverManager {
	
	public void DBdriver(Driver driver){
		setDriver(driver);
		getDriver().method();
	}

}
