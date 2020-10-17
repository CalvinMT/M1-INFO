import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Triggers {
	
	private Connection connection;
	
	
	
	public Triggers (Connection c) {
		connection = c;
	}
	
	
	
	public void createGuardianFunctionDelete () throws SQLException {
		Statement statement = connection.createStatement();
		statement.executeUpdate(""
				+ "CREATE TRIGGER guardian_function_delete "
				+ "AFTER DELETE ON LesGardiens "
				+ "FOR EACH ROW "
				+ "BEGIN "
					+ "INSERT INTO LesHistoiresAff "
					+ "VALUES(sysdate, :old.noCage, :old.nomE);"
				+ "END;");
		statement.close();
	}
	
}
