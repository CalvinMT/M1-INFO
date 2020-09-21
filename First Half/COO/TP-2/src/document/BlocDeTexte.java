package document;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class BlocDeTexte {
	
	private List <Paragraphe> blocDeTexte;
	
	
	
	public BlocDeTexte () {
		blocDeTexte = new ArrayList <> ();
	}
	
	
	
	public void ajouteParagraphe (Paragraphe p) {
		blocDeTexte.add(p);
	}
	
	public void fixeLargeur (int l) {
		blocDeTexte.stream().forEach(p -> {
			p.fixeLargeur(l);
		});
	}
	
	
	
	public void ecris (PrintStream p) {
		blocDeTexte.stream().forEach(paragraphe -> {
			p.println(paragraphe.toString());
		});
	}
	
	public String toString () {
		String result = "";
		result = result.concat("Bloc de texte\n");
		result = result.concat(Integer.toString(blocDeTexte.size()));
		return result;
	}
	
}
