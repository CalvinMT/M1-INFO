package document;

public class TexteDroite extends Texte {
	
	public TexteDroite (String t) {
		super(t);
	}
	
	
	
	@Override
	public String toString () {
		int justification;
		StringBuilder resultat = new StringBuilder();

		justification = largeur - texte.length();
		for (int i = 0; i < justification; i++) {
			resultat.append(SEPARATEUR);
		}
		resultat.append(texte);
		return resultat.toString();
	}
	
}
