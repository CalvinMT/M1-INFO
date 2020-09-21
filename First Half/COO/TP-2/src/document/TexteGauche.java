package document;

public class TexteGauche extends Texte {
	
	public TexteGauche (String t) {
		super(t);
	}
	
	
	
	@Override
	public String toString () {
		StringBuilder resultat = new StringBuilder();
		resultat.append(texte);
		return resultat.toString();
	}
	
}
