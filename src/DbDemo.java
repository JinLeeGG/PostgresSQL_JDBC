import java.sql.Connection;
import java.util.Properties;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The DbDemo Class contains nothing but a main method that demonstrates an
 * extremely simple use of JDBC to connect to a database. In any real
 * application, a more sophisticated approach will be needed, but this
 * class demonstrates the bare minimum needed for a database connection.
 * 
 * @author John MacCormick
 * @version December 2021
 *
 */
public class DbDemo {

	public static void main(String[] args) {
		Connection connection = null;
		String PG_USER = "postgres";
		// String PG_PASSWORD = "mypassword";
		String PG_PASSWORD = System.getenv("PGPASSWORD");

		// The connectionProperties object will store the username and
		// password for connecting to the database.
		Properties connectionProperties = new Properties();
		connectionProperties.put("user", PG_USER);
		connectionProperties.put("password", PG_PASSWORD);

		// The string jdbcUrl specifies the details of how jdbc should
		// contact the database server.
		String jdbcUrl = "jdbc:postgresql://localhost:5432/";
		try {
			connection = DriverManager.getConnection(jdbcUrl,
					connectionProperties);
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		// Create a statement that can be used to send SQL queries to the
		// database server.
		try (Statement statement = connection.createStatement()) {
			ResultSet rs = null;
			// Specify that we are using the wine schema.
			statement.execute("set search_path to wine");
			// Execute a SELECT query in SQL
			rs = statement.executeQuery("SELECT * FROM SUPPLIER");
			// Iterate over the results returned by the query, using
			// getString() to obtain values according to column names.
			while (rs.next()) {
				String supNum = rs.getString("SUPNR");
				String supName = rs.getString("SUPNAME");
				int supStatus = rs.getInt("SUPSTATUS");
				// Print the returned results to the console.
				System.out.format("%s %s %d\n", supNum, supName,
						supStatus);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

}