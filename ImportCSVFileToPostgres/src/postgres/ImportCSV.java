package postgres;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;

public class ImportCSV {

		public static void main(String[] args) throws SQLException, IOException, URISyntaxException {
			System.out.println("-------- PostgreSQL " + "JDBC Connection Testing ------------");

			try {

				Class.forName("org.postgresql.Driver");

			} catch (ClassNotFoundException e) {

				System.out.println("Where is your PostgreSQL JDBC Driver? " + "Include in your library path!");
				e.printStackTrace();
				
				return;
			}

			System.out.println("PostgreSQL JDBC Driver Registered!");

			Connection connection = null;

			try {
				connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/testdb", "postgres", "JSE8963$");

			} catch (SQLException e) {

				System.out.println("Connection Failed! Check output console");
				e.printStackTrace();
				return;

			}

			if (connection != null) {
				System.out.println("You made it, take control of your database now!");

				CopyManager copyManager = new CopyManager((BaseConnection) connection);

				copyManager.copyIn("COPY employee FROM STDIN WITH DELIMITER ';' CSV HEADER",
						getFileReader("employee_postgres.csv"));

			} else {
				System.out.println("Failed to make connection!");
			}
		}

		private static FileReader getFileReader(String path) throws FileNotFoundException, URISyntaxException {
			return new FileReader(new ImportCSV().getPath(path).toString());
		}

		private Path getPath(String fileName) throws URISyntaxException {
			return Paths.get(getClass().getClassLoader().getResource(fileName).toURI());
		}

	}

