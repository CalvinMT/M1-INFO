package document;

public class ParagrapheDroite extends Paragraphe {
	
	public ParagrapheDroite () {
		super();
	}
	
	
	
	@Override
	public String toString () {
		String result = "";
		String currentLine = "";
		for (String mot : paragraphe) {
			if ((currentLine.length() + mot.length()) > largeur) {
				currentLine = currentLine.trim();
				int nbEspacesGauche = largeur - currentLine.length();
				for (int i=0; i<nbEspacesGauche; i++) {
					currentLine = " ".concat(currentLine);
				}
				currentLine = currentLine.concat("\n");
				result = result.concat(currentLine);
				currentLine = "";
			}
			currentLine = currentLine.concat(mot);
			// Check to add space
			if (currentLine.length() < largeur) {
				currentLine = currentLine.concat(" ");
			}
		}
		return result;
	}
	
}
