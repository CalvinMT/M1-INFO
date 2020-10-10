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
	
	/**
	 * 
	 * @throws SQLException
	 */
	public void changeGuardianAffectation () throws SQLException {
		String guardian = "";
		String cageToWithdraw = "";
		String cageToAdd = "";
		System.out.println(" - Changer la fonction d'un gardien - ");
		System.out.print("Nom du gardien : ");
		guardian = LectureClavier.lireChaine();
		
		System.out.println("Cages gard�es par le gardien " + guardian + " : ");
		Statement statement = connection.createStatement();
		ResultSet guardianCages = statement.executeQuery(""
				+ "SELECT noCage "
				+ "FROM LesGardiens "
				+ "WHERE nomE = '" + guardian + "' ");
		while (guardianCages.next()) {
			System.out.println("	- " + guardianCages.getString(1));
		}
		System.out.println();
		statement.close();
		
		System.out.print("Cage � retirer de la fonction du gardien " + guardian + " : ");
		cageToWithdraw = LectureClavier.lireChaine();
		System.out.println();
		statement = connection.createStatement();
		statement.executeUpdate(""
				+ "DELETE "
				+ "FROM LesGardiens "
				+ "WHERE nomE = '" + guardian + "' AND noCage = " + cageToWithdraw + " ");
		statement.close();
		
		System.out.println("Cages dont la sp�cialit� est compatible avec le gardien " + guardian + " : ");
		statement = connection.createStatement();
		ResultSet guardianSpecialtySuggestion = statement.executeQuery(""
				+ "SELECT A.noCage, fonction "
				+ "FROM ( "
		    		+ "SELECT noCage, fonction "
		    		+ "FROM LesCages C "
		    		+ "INNER JOIN LesSpecialites S "
		    		+ "ON C.fonction = S.fonction_cage "
		    		+ "WHERE S.nomE = '" + guardian + "' "
				+ ") A "
		    	
				+ "LEFT JOIN LesGardiens G "
				+ "ON A.noCage = G.noCage "
				+ "WHERE G.noCage IS NULL");
		while (guardianSpecialtySuggestion.next()) {
			System.out.println("	- " + guardianSpecialtySuggestion.getString(1));
		}
		System.out.println();
		statement.close();
		
		System.out.print("Quelle cage attribuer au gardien " + guardian + " : ");
		cageToAdd = LectureClavier.lireChaine();
		System.out.println();
		statement = connection.createStatement();
		statement.executeUpdate(""
				+ "INSERT INTO LesGardiens "
				+ "VALUES (" + cageToAdd + ", '" + guardian + "')");
		statement.close();
	}
}
