import java.sql.Connection;
import java.util.Properties;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * A QueryExecutor object can be used to send SQL commands and queries to a
 * Postgres database server and for printing the results of queries. This
 * class is written as a simple tutorial example. It takes no account of
 * issues such as security and efficiency.
 * 
 * @author John MacCormick
 * @version August 2025
 *
 */
public class QueryExecutor {
	private static final String PG_USER = "postgres";

	// Important note: in any real-world application, passwords would not
	// be stored in the source code.
	private static final String PG_PASSWORD = "whqdms0B";

	private static final String PG_HOST = "localhost";

	private static final String PG_PORT_NUMBER = "5432";

	private static String SUPABASE_CONN = null;
	// private static String SUPABASE_CONN = "jdbc:postgresql://aws-0-us-east-1.pooler.supabase.com:5432/postgres?user=postgres.xnnnhehnsucsmdmbggvu&password=l1vZlecIoqQLjCkl";

	private String schema_name = "wine";
	
	Connection connection = null;

	/**
	 * Construct a new QueryExecutor object and connect it to the desired Postgres
	 * database, as specified by the class constants such as PG_IP_ADDRESS,
	 * PG_PORT_NUMBER, etc.
	 */
	public QueryExecutor() {
		initialize();
	}

	/**
	 * Construct a new QueryExecutor object and connect it to the desired Postgres
	 * database, as specified by the class constants such as PG_IP_ADDRESS,
	 * PG_PORT_NUMBER, etc.
	 * 
	 * @param schemaName The name of the Postgres database to connect to
	 */
	public QueryExecutor(String schemaName) {
		this.schema_name = schemaName;
		initialize();
	}

	private void initialize() {
		Properties connectionProperties = new Properties();
		connectionProperties.put("user", PG_USER);
		connectionProperties.put("password", PG_PASSWORD);

		// Connect to Postgres
		try {
			if (SUPABASE_CONN == null) {
				String connString = "jdbc:" + "postgresql" + "://" + PG_HOST + ":" + PG_PORT_NUMBER + "/";
				// System.out.println("Connecting to database with connection string:\n" + connString);
				connection = DriverManager.getConnection(connString, connectionProperties);
			} else {
				// System.out.println("Connecting to supabase with connection string:\n" + SUPABASE_CONN);
				connection = DriverManager.getConnection(SUPABASE_CONN);
			}
		} catch (SQLException e) {
			handleSQLException(e);
		}
		System.out.println("Successfully connected to Postgres.");

		// Switch to using the desired schema
		// execute("use " + schema_name);
		execute("set search_path to " + schema_name);
		System.out.format("Using schema '%s'.\n\n", schema_name);
	}

	/**
	 * @return the Connection object representing the current database
	 *         session.
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * A simpler handler for a SQLException. It prints the stack trace and
	 * terminates the program.
	 * 
	 * @param e The SQLException to be handled
	 */
	public void handleSQLException(SQLException e) {
		e.printStackTrace();
		System.exit(0);
	}

	private void printResultSet(ResultSet resultSet) {
		final String separator = " | ";
		System.out.println("Result set:");
		try {
			ResultSetMetaData md = resultSet.getMetaData();
			int numCols = md.getColumnCount();

			// print column labels
			for (int i = 1; i <= numCols; i++) {
				System.out.print(md.getColumnLabel(i));
				if (i < numCols) {
					System.out.print(separator);
				}
			}
			System.out.println();

			// print contents of each row returned
			int rowCounter = 0;
			while (resultSet.next()) {
				rowCounter++;
				StringBuilder sb = new StringBuilder();
				for (int i = 1; i <= numCols; i++) {
					String result = resultSet.getString(i);
					sb.append(result);
					if (i < numCols) {
						sb.append(separator);
					}
				}
				System.out.println(sb);
			}
			System.out.println("Number of rows: " + rowCounter);

		} catch (SQLException e) {
			handleSQLException(e);
		}
	}

	/**
	 * Print a message to the console, describing the SQL query that has
	 * just been executed. Long queries are truncated and the query is
	 * reformatted to make the message more readable.
	 * 
	 * @param query The SQL query that has just been executed.
	 */
	private void printExecutionMessage(String query) {
		final int maxLen = 55;
		String truncated_query;
		if (query.length() > maxLen) {
			truncated_query = query.substring(0, maxLen) + "...";
		} else {
			truncated_query = query;
		}
		// Eliminate newlines and consecutive spaces
		truncated_query = truncated_query.replace('\n', ' ').trim()
				.replaceAll(" +", " ");

		System.out.format("Executed the query \"%s\"\n", truncated_query);
	}

	/**
	 * Send an SQL command to the database server. This method is used for
	 * sending commands that do not return results. For queries that return
	 * results, use executeQuery().
	 * 
	 * @param sqlString The SQL command to be executed.
	 */
	public void execute(String sqlString) {
		try (Statement statement = connection.createStatement()) {
			statement.execute(sqlString);
		} catch (SQLException e) {
			handleSQLException(e);
		}
		printExecutionMessage(sqlString);
	}

	/**
	 * Send an SQL query to the database server and optionally print the
	 * results. This method is used for sending queries that return
	 * results. For commands that do not return results, use execute().
	 * 
	 * @param query        The SQL query to be executed.
	 * @param printResults true if the results should be printed, false
	 *                     otherwise.
	 */
	public void executeQuery(String query, boolean printResults) {
		try (Statement statement = connection.createStatement()) {
			ResultSet resultSet = statement.executeQuery(query);
			printExecutionMessage(query);
			if (printResults) {
				printResultSet(resultSet);
			}
		} catch (SQLException e) {
			handleSQLException(e);
		}
	}

	/**
	 * Send an SQL query to the database server and print the results. This
	 * method is used for sending queries that return results. For commands
	 * that do not return results, use execute().
	 * 
	 * @param query The SQL query to be executed.
	 */
	public void executeQuery(String query) {
		executeQuery(query, true);
	}

	public static void main(String[] args) {
		QueryExecutor qe = new QueryExecutor("wine");

		// This is an example of how to execute a query.
		qe.executeQuery("SELECT * FROM product");
	}
}