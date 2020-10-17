import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
				+ "SELECT A.noCage, A.fonction, A.nomE "
				+ "FROM "
					+ "(SELECT C.noCage, C.fonction, S.nomE "
					+ "FROM LesCages C "
					+ "INNER JOIN LesSpecialites S "
					+ "ON C.fonction = S.fonction_cage "
					+ "WHERE S.nomE = 'Lachaize') A "
				+ "MINUS "
				+ "SELECT noCage, fonction_cage, nomE "
				+ "FROM "
					+ "(SELECT G.noCage, S.fonction_cage, G.nomE "
					+ "FROM LesGardiens G "
					+ "INNER JOIN LesSpecialites S "
					+ "ON G.nomE = S.nomE "
					+ "WHERE S.nomE = 'Lachaize') B ");
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
	
	
	 /**
	  * 
	  * @throws SQLException
	  */
	public void changeCageOfAnimal () throws SQLException {
		String animalName = "";
		String animalType = "";
		String animalCageType = "";
		int animalCage = 0;
		int newCage = 0;
		System.out.println(" - Changer un animal de cage - ");
		System.out.println();
		Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet animals = statement.executeQuery(""
				+ "SELECT nomA, type_an, fonction_cage, noCage "
				+ "FROM LesAnimaux ");
		
		while (animals.next()) {
			System.out.printf("	- " + "%-20s%-15s%-20s%s\n", animals.getString(1), animals.getString(2), animals.getString(3), animals.getString(4));
		}
		System.out.println();
		System.out.print("Nom de l'animal : ");
		animalName = LectureClavier.lireChaine();
		
		animals.beforeFirst();
		while (animals.next()  &&  ! animals.getString(1).equals(animalName)) {
			// do nothing
		}
		if (animals.isAfterLast()) {
			System.out.println("\n/!\\ L'animal " + animalName + "n'exite pas dans ce zoo.\n");
			return;
		}
		else {
			animalType = animals.getString(2);
			animalCageType = animals.getString(3);
			animalCage = animals.getInt(4);
		}
		statement.close();
		System.out.println(animalName + ", " + animalType + ", est dans la cage " + animalCage + ".");
		System.out.println();
		System.out.println("Dans quelle cage d�placer " + animalName + " ?");
		System.out.print("Num�ro de cage : ");
		newCage = LectureClavier.lireEntier("");
		
		// Update animal's cage
		statement = connection.createStatement();
		statement.executeUpdate(""
				+ "UPDATE LesAnimaux "
				+ "SET noCage=" + newCage + " "
				+ "WHERE nomA='" + animalName + "' ");
		statement.close();
		
		System.out.println();
		System.out.println(animalName + " a �t� d�plac� dans la cage " + newCage + ".");
	}
	
	
	
	/**
	 * 
	 * @throws SQLException
	 */
	public void getStatisticsGuardiansPerCage () throws SQLException {
		DatabaseMetaData metaDataBase = connection.getMetaData();
		ResultSet dataBaseTables = metaDataBase.getTables(null, null, "STATISTIQUEGARDIENPARCAGE", null);
		Statement statement;
		if (dataBaseTables.next()) {
	        statement = connection.createStatement();
	        statement.executeUpdate(""
	        		+ "DROP TABLE StatistiqueGardienParCage");
	        statement.close();
		}
		System.out.println(" - Statistique : nombre de gardien par cage - ");
        statement = connection.createStatement();
        statement.executeUpdate(""
        		+ "CREATE TABLE StatistiqueGardienParCage ( "
        			+ "noCage number(3), "
        			+ "nbGardien number(20), "
        			+ "constraint StatistiqueGardienParCage_C1 primary key (noCage, nbGardien), "
        			+ "constraint StatistiqueGardienParCage_C2 foreign key (noCage) references LesCages (noCage), "
        			+ "constraint StatistiqueGardienParCage_C3 check (nbGardien between 0 and 999) "
    			+ ") ");
        statement.close();
        statement = connection.createStatement();
        ResultSet cages = statement.executeQuery(""
        		+ "SELECT noCage "
        		+ "FROM LesCages ");
        PreparedStatement preparedStatementNbGuardians = connection.prepareStatement(""
        		+ "SELECT count(*) "
        		+ "FROM LesGardiens "
        		+ "WHERE noCage = ? ");
        PreparedStatement preparedStatementInsertStat = connection.prepareStatement(""
        		+ "INSERT INTO StatistiqueGardienParCage "
        		+ "VALUES (?, ?) ");
        ResultSet nbGuardian;
        while (cages.next()) {
        	preparedStatementNbGuardians.setInt(1, cages.getInt(1));
        	nbGuardian = preparedStatementNbGuardians.executeQuery();
        	nbGuardian.next();
        	preparedStatementInsertStat.setInt(1, cages.getInt(1));
        	preparedStatementInsertStat.setInt(2, nbGuardian.getInt(1));
        	preparedStatementInsertStat.executeUpdate();
        }
        statement.close();
        preparedStatementNbGuardians.close();
        preparedStatementInsertStat.close();
        statement = connection.createStatement();
        ResultSet table = statement.executeQuery(""
        		+ "SELECT * "
        		+ "FROM StatistiqueGardienParCage ");
        ResultSetMetaData metaTable = table.getMetaData();
        int nbColumn = metaTable.getColumnCount();
        System.out.println("noCage	nbGardien");
        System.out.println("------	---------");
        while (table.next()) {
        	for (int i=1; i<=nbColumn; i++) {
        		System.out.print(table.getString(i) + "	");
        	}
        	System.out.println();
        }
        statement.close();
	}
	
	
	
	/**
	 * 
	 * @throws SQLException
	 */
	public void getStatisticsCagesPerGuardian () throws SQLException {
		DatabaseMetaData metaDataBase = connection.getMetaData();
		ResultSet dataBaseTables = metaDataBase.getTables(null, null, "STATISTIQUECAGEPARGARDIEN", null);
		Statement statement;
		if (dataBaseTables.next()) {
	        statement = connection.createStatement();
	        statement.executeUpdate(""
	        		+ "DROP TABLE StatistiqueCageParGardien");
	        statement.close();
		}
		System.out.println(" - Statistique : nombre de cage par gardien - ");
        statement = connection.createStatement();
        statement.executeUpdate(""
        		+ "CREATE TABLE StatistiqueCageParGardien ( "
    				+ "nomE varchar2(20), "
        			+ "nbCage number(3), "
        			+ "constraint StatistiqueCageParGardien_C1 primary key (nomE, nbCage), "
        			+ "constraint StatistiqueCageParGardien_C2 foreign key (nomE) references LesEmployes (nomE), "
        			+ "constraint StatistiqueCageParGardien_C3 check (nbCage between 0 and 999) "
    			+ ") ");
        statement.close();
        statement = connection.createStatement();
        ResultSet gardiens = statement.executeQuery(""
        		+ "SELECT nomE "
        		+ "FROM LesEmployes ");
        PreparedStatement preparedStatementNbCages = connection.prepareStatement(""
        		+ "SELECT count(*) "
        		+ "FROM LesGardiens "
        		+ "WHERE nomE = ? ");
        PreparedStatement preparedStatementInsertStat = connection.prepareStatement(""
        		+ "INSERT INTO StatistiqueCageParGardien "
        		+ "VALUES (?, ?) ");
        ResultSet nbCages;
        while (gardiens.next()) {
        	preparedStatementNbCages.setString(1, gardiens.getString(1));
        	nbCages = preparedStatementNbCages.executeQuery();
        	nbCages.next();
        	preparedStatementInsertStat.setString(1, gardiens.getString(1));
        	preparedStatementInsertStat.setInt(2, nbCages.getInt(1));
        	preparedStatementInsertStat.executeUpdate();
        }
        statement.close();
        preparedStatementNbCages.close();
        preparedStatementInsertStat.close();
        statement = connection.createStatement();
        ResultSet table = statement.executeQuery(""
        		+ "SELECT * "
        		+ "FROM StatistiqueCageParGardien ");
        ResultSetMetaData metaTable = table.getMetaData();
        int nbColumn = metaTable.getColumnCount();
        System.out.println("nomE		nbCage");
        System.out.println("---------------	------");
        while (table.next()) {
        	for (int i=1; i<=nbColumn; i++) {
        		System.out.print(table.getString(i));
        		if (table.getString(i).length() < 8) {
        			System.out.print("	");
        		}
        		if (table.getString(i).length() < 16) {
        			System.out.print("	");
        		}
        	}
        	System.out.println();
        }
        statement.close();
	}
	
	
	
	/**
	 * 
	 * @throws SQLException
	 */
	public void getStatistics () throws SQLException {
		getStatisticsGuardiansPerCage();
  	    System.out.println();
        getStatisticsCagesPerGuardian();
	}
	
}
