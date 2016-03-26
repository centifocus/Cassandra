package wangliqiu.test.sql;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Struct;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.Query;

import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

public class MPP {
	protected static Logger logger = Logger.getLogger(MPP.class);
	Connection conn;

	@Before
	public void init() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Class.forName("com.gbase.jdbc.Driver").newInstance();
		Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();

		// crm
		// conn = DriverManager.getConnection("jdbc:oracle:thin:@10.145.196.67:1521:siebeldb2",
		// "eai_query", "copydb2012");

		// ods
		// conn = DriverManager.getConnection("jdbc:oracle:thin:@10.7.66.201:1521:ods", "s_esb",
		// "s_esb");

		// ods_hx
		// conn = DriverManager.getConnection("jdbc:oracle:thin:@10.7.9.201:1521:dmdev", "csb_test",
		// "csb_test");

		// mpp
		// conn = DriverManager.getConnection("jdbc:gbase://10.7.6.123:5258/esbdb", "s_esb",
		// "123456");

		// conn = DriverManager.getConnection("jdbc:gbase://10.7.6.6:5258/esbdb", "s_esb",
		// "dianxin123456");

		// conn = DriverManager.getConnection("jdbc:gbase://10.7.6.6:5258/testdb", "u_test_lixiang",
		// "lixiang");

		conn = DriverManager.getConnection("jdbc:oracle:thin:@10.7.74.102:1521:tytsdb", "csbquery", "CsB#15sh");
		System.out.println(conn);

	}

	@Test
	public void execute() throws IOException {
		CallableStatement callStat = null;

		String sql = "CALL sp_awtdb_stqd_d_qurey(?,?,?,?,?,?)";

		// String sql = " {CALL  csb_test.PKG_VIP_CUST_LBL_QUERY.SP_VIP_CUST_LBL_QUERY(?,?,?,?,?)}";

		try {
			callStat = conn.prepareCall(sql);

			callStat.setString(1, "月");
			callStat.setString(2, "WB_A");
			callStat.setString(3, "中小渠道");
			callStat.setString(4, "南区");
			callStat.setString(5, null);
			callStat.setString(6, null);

			List<String> values = new ArrayList<>();
			// values.add("202124344830");
			// ArrayDescriptor desc = ArrayDescriptor.createDescriptor("T_CUST_CODE", conn);
			// Array ParamValueArray = new ARRAY(desc, conn, values.toArray()); // Array
			// // ParamValueArray = conn.createArrayOf("T_CUST_CODE", values.toArray());
			//
			// String[] values_instru = new String[] { "17701797957" };
			// ArrayDescriptor desc_instru = ArrayDescriptor.createDescriptor("T_PROD_INST_NUM",
			// conn);
			// Array array = new ARRAY(desc_instru, conn, values_instru);
			//
			// callStat.setArray(2, ParamValueArray);
			// callStat.setArray(1, array);
			//
			// // callStat.registerOutParameter(3, OracleTypes.CURSOR);
			// callStat.registerOutParameter(3, Types.ARRAY, "RES_VIP_CUST_LBL_INFOS");
			// callStat.registerOutParameter(4, Types.INTEGER);
			// callStat.registerOutParameter(5, Types.VARCHAR);

			boolean hadResults = callStat.execute();
			System.out.println("hadResults  " + hadResults);

			int k = 0;
			// Object[] objs = (Object[]) callStat.getArray(3).getArray();
			// System.out.println("length" + objs.length + objs[0]);

			/*
			 * The result set contains one row for each array element, with two columns in each row.
			 * The second column stores the element value; the first column stores the index
			 */
			/*
			 * ResultSet rs = callStat.getArray(3).getResultSet(); while (rs.next()) {
			 * System.out.println("index: " + rs.getString(1)); // ResultSet rSet =
			 * rs.getArray(2).getResultSet();
			 * 
			 * // ResultSetMetaData rsmd = rs.getMetaData();
			 * 
			 * Struct struct = (Struct) (rs.getObject(2)); if (struct != null) { for (int j = 0; j <
			 * struct.getAttributes().length; j++) {
			 * 
			 * System.out.println(struct.getAttributes()[j]); } } }
			 */

			// for (Object obj : objs) {
			// k++;
			// Struct struct = (Struct) obj;
			// System.out.println("ddddddddddddddd" + struct.getAttributes().length + "ggggggggggg"
			// + struct.getSQLTypeName());
			// if (struct != null) {
			// for (int j = 0; j < struct.getAttributes().length; j++) {
			//
			// System.out.println(struct.getAttributes()[j]);
			// }
			// }
			//
			// }

			while (hadResults) {
				ResultSet rs = callStat.getResultSet();
				ResultSetMetaData rsmd = rs.getMetaData();
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					System.out.println("  " + rsmd.getColumnLabel(i + 1));

				}
				while (rs.next()) {
					System.out.println(rs.getString(1));
					k++;
				}

				hadResults = callStat.getMoreResults();
			}
			System.out.println(k);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
