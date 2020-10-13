import java.io.IOException;
import java.sql.*;

public class squelette_appli {
	
	static final String CONN_URL = "jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag";
	
	static String USER = "A COMPLETER";
	static String PASSWD = "A COMPLETER";

	static Connection conn; 
	
	
    public static void main(String args[]) {

        try {
	  	    System.out.print("Username: "); 
	        USER = LectureClavier.lireChaine();
	  	    System.out.print("Password: "); 
	        PASSWD = LectureClavierSilencieuse.readPassword("", USER);
	        
	  	    // Enregistrement du driver Oracle
	  	    System.out.print("Loading Oracle driver... "); 
	  	    DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
	  	    System.out.println("loaded");
	  	    
	  	    // Etablissement de la connection
	  	    System.out.print("Connecting to the database... "); 
	 	    conn = DriverManager.getConnection(CONN_URL,USER,PASSWD);
	   	    System.out.println("connected");
	  	    
	  	    // Desactivation de l'autocommit
		  	conn.setAutoCommit(false);
	  	    System.out.println("Autocommit disabled");
	
		    // code metier de la fonctionnalite
	  	    System.out.println();
	  	    Fonctionnalites fonctionnalites = new Fonctionnalites(conn);
	  	    conn.commit();
	  	    System.out.println();
	
	  	    // Liberation des ressources et fermeture de la connexion...
	 		conn.close(); 
	 	    
	  	    System.out.println("bye.");
	  	    
  	    	// traitement d'exception
          } catch (SQLException e) {
              System.err.println("failed");
              System.out.println("Affichage de la pile d'erreur");
  	          e.printStackTrace(System.err);
              System.out.println("Affichage du message d'erreur");
              System.out.println(e.getMessage());
              System.out.println("Affichage du code d'erreur");
  	          System.out.println(e.getErrorCode());	    

          } catch (IOException e) {
			e.printStackTrace();
		}
     }
	

}