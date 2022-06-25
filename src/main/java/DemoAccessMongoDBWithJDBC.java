import java.sql.*;

/**
 * DemoAccessMongoDBWithJDBC
 */
public class DemoAccessMongoDBWithJDBC {
    
	private static final String TABLE_NAME = "test_collection2";

    public DemoAccessMongoDBWithJDBC() {
		String url="jdbc:mongodb://localhost:27017/sample_mflix";
		Connection con;
		try {
			con = DriverManager.getConnection(url);
			
			Statement stmt = con.createStatement();
			String sql = "SELECT * FROM " + TABLE_NAME + ";";
			ResultSet rst = stmt.executeQuery(sql);

			ResultSetMetaData meta = rst.getMetaData();
			int numColumns = meta.getColumnCount();
			System.out.print(meta.getColumnName(1));
			for (int j = 2; j <= meta.getColumnCount(); j++)
				System.out.print(", " + meta.getColumnName(j));
			System.out.println();
			while (rst.next())
			{
				System.out.print(rst.getObject(1));
				for (int j = 2; j <= numColumns; j++)
					System.out.print(", " + rst.getObject(j));
				System.out.println();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
    }
}