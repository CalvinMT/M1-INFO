package document;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Document {
	
	private Entete entete;
	private BlocDeTexte prologue;
	private List <Chapitre> chapitres;
	
	
	
	public Document (Entete e, InputStream in) {
		// TODO - inputStream
		entete = e;
		prologue = new BlocDeTexte();
		chapitres = new ArrayList <> ();
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
