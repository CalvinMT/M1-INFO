import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Fonctionnalites {
	
	private Connection connection;
	
	
	
	public Fonctionnalites (Connection c) {
		this.connection = c;
	}
	
	
	
	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	public int changeCageFunction () throws SQLException {
		int nbStatementChanged = 0;
		String cage = "";
		String fonction = "";
		System.out.println(" - Changer la fonction d'une cage - ");
		System.out.print("Cage : ");
        cage = LectureClavier.lireChaine();
		System.out.print("Nouvelle fonction : ");
        fonction = LectureClavier.lireChaine();
        
        Statement statement = connection.createStatement();
        nbStatementChanged = statement.executeUpdate(""
        		+ "UPDATE LesCages "
        		+ "SET fonction = '" + fonction + "' "
				+ "WHERE noCage = " + Integer.parseInt(cage) + " ");
        statement.close();
        return nbStatementChanged;
	}
}
