package document;
public class TexteCentre {
	
	private String SEPARATEUR = " ";
	
	private String texte;
	private int largeur;
	
	
	
	public TexteCentre (String t) {
		texte = t;
		largeur = t.length();
	}
	
	
	
	public void fixeLargeur (int l) {
		if (l > largeur) {
			largeur = l;
		}
	}
	
	public void setSeparateur (String s) {
		SEPARATEUR = s;
	}
	
	
	
	public String texte () {
		return texte;
	}
	
	public String toString () {
		String result = "";
		int l = largeur - texte.length();
		for (int i=0; i<(l/2); i++) {
			result = result.concat(SEPARATEUR);
		}
		result = result.concat(texte);
		for (int i=0; i<(l/2); i++) {
			result = result.concat(SEPARATEUR);
		}
		if (texte.length()%2 == 1  &&  largeur%2 == 0  ||  texte.length()%2 == 0  &&  largeur%2 == 1) {
			result = result.concat(SEPARATEUR);
		}
		return result;
	}

}
