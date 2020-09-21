package document;

import java.io.PrintStream;

public class Chapitre {
	
	private Texte titre;
	private BlocDeTexte blocDeTexte;
	
	
	
	public Chapitre (String t) {
		titre = new TexteCentre(t);
		blocDeTexte = new BlocDeTexte();
	}
	
	
	
	public void ajoute (Paragraphe p) {
		blocDeTexte.ajouteParagraphe(p);
	}
	
	public void fixeLargeur (int l) {
		titre.fixeLargeur(l);
		blocDeTexte.fixeLargeur(l);
	}
	
	
	
	public BlocDeTexte getBlocDeTexte () {
		return blocDeTexte;
	}
	
	public void ecris (PrintStream p) {
		p.println(titre.texte());
		blocDeTexte.ecris(p);
	}
	
	public String toString () {
		String result = "";
		result = result.concat("Chapitre\n");
		result = result.concat(titre.toString() + "\n");
		result = result.concat(blocDeTexte.toString());
		return result;
	}
	
}
