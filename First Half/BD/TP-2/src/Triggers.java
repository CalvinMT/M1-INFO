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
	
	
	
	public void createAnimalCageChange () throws SQLException {
		Statement statement = connection.createStatement();
		statement.executeUpdate(""
				+ "CREATE TRIGGER animal_cage_change "
				+ "BEFORE UPDATE ON LesCages "
				+ "FOR EACH ROW "
				+ "DECLARE "
					+ "animalFonction LesAnimaux%fonction_cage;"
					+ "cageFonction LesCages%fonction;"
				+ "BEGIN "
					+ "SELECT fonction_cage INTO animalFonction "
					+ "FROM LesAnimaux "
					+ "WHERE nomA=:old.nomA AND noCage=:old.noCage;"
					+ "SELECT fonction INTO cageFonction "
					+ "FROM LesCages "
					+ "WHERE noCage=:new.noCage;"
					+ "IF animalFonction<>cageFonction "
						+ "THEN raise_application_error(-20100, 'La fonction de la nouvelle cage est incompatible avec la fonction de l'animal.') "
					+ "END IF;"
				+ "END;");
		statement.close();
	}
	
}
