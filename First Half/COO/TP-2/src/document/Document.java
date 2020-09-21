package document;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Document {
	
	private Entete entete;
	private BlocDeTexte prologue;
	private List <Chapitre> chapitres;
	
	
	
	public Document (Entete e, InputStream in) {
		entete = e;
		prologue = new BlocDeTexte();
		chapitres = new ArrayList <> ();
		
		scanInputStream(in);
	}
	
	
	
	private void scanInputStream (InputStream in) {
		Scanner scanner = new Scanner(in);
		BlocDeTexte prologue = this.prologue;
		Paragraphe paragraphe = null;
		while (scanner.hasNextLine()) {
			String ligne = scanner.nextLine().trim();
			if (ligne.length() == 0) {
				if (paragraphe != null) {
					prologue.ajouteParagraphe(paragraphe);
					paragraphe = null;
				}
			}
			else {
				String[] mots = ligne.split("\\s\\s*");
				if (mots[0].equals("CHAPITRE")) {
					chapitres.add(new Chapitre(ligne));
					prologue = chapitres.get(chapitres.size()-1).getBlocDeTexte();
				} else {
					if (paragraphe == null) {
						paragraphe = new ParagrapheGauche();
					}
					for (int i = 0; i < mots.length; i++) {
						paragraphe.ajoute(mots[i]);
					}
				}
			}
		}
		scanner.close();
	}
	
	
	
	public void fixeLargeur (int l) {
		entete.fixeLargeur(l);
		prologue.fixeLargeur(l);
		chapitres.stream().forEach(chapitre -> {
			chapitre.fixeLargeur(l);
		});
	}
	
	public void ecris (PrintStream p) {
		p.println(entete.toString());
		prologue.ecris(p);
		chapitres.stream().forEach(chapitre -> {
			chapitre.ecris(p);
		});
	}
	
}
