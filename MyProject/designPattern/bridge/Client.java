package bridge;

/**
 * JDBC桥DriverManager，JDBC进行连接数据库的时候，在各个数据库之间进行切换，
 * 基本不需要动代码，原因就是JDBC提供统一接口，每个数据库提供各自的实现，DriverManager来桥接就行了。
 *
 */
public class Client {

	public static void main(String[] args){
		DriverManager dm = new MyDriverManager();
		Driver mysql = new MysqlDriver();
		Driver oracle = new OracleDriver();
		
		dm.DBdriver(mysql);
		dm.DBdriver(oracle);
	}
	
}
