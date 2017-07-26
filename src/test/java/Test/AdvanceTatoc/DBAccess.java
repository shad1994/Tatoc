package Test.AdvanceTatoc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.ResultSetMetaData;

public class DBAccess {

	public String findValue(String selectValue, String tableName, String whereCol, String whereColValue)
			throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://10.0.1.86/tatoc";
		String username = "tatocuser", password = "tatoc01";
		Connection con = DriverManager.getConnection(url, username, password);
		Statement stmt = con.createStatement();
		String query = "Select " + selectValue + " from " + tableName + " where " + whereCol + "= '" + whereColValue
				+ "'";
		System.out.println(query);
		ResultSet rs1 = stmt.executeQuery(query);
		rs1.next();
		System.out.println(rs1.getString(1));
		String value = rs1.getString(1);
		return value;

	}

	public void printTables() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://10.0.1.86/tatoc";
		String username = "tatocuser", password = "tatoc01";
		Connection con = DriverManager.getConnection(url, username, password);
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select * from identity");
		ResultSetMetaData metadata = (ResultSetMetaData) rs.getMetaData();

		int columnCount = metadata.getColumnCount();
		for (int i = 1; i <= columnCount; i++) {
			System.out.print(metadata.getColumnName(i) + "  ");
		}
		System.out.println();

		while (rs.next()) {
			String row = "";
			for (int i = 1; i <= columnCount; i++) {
				row = rs.getString(i) + "  ";
				System.out.print(row);
			}
			System.out.println();

		}

		ResultSet rs5 = stmt.executeQuery("select * from credentials");
		ResultSetMetaData metadata5 = (ResultSetMetaData) rs5.getMetaData();

		int columnCount5 = metadata5.getColumnCount();
		for (int i = 1; i <= columnCount5; i++) {
			System.out.print(metadata5.getColumnName(i) + "  ");
		}
		System.out.println();

		while (rs5.next()) {
			String row = "";
			for (int i = 1; i <= columnCount5; i++) {
				row = rs5.getString(i) + "  ";
				System.out.print(row);
			}
			System.out.println();

		}

	}

}
