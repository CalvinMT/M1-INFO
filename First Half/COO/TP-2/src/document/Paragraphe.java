package document;

import java.util.ArrayList;
import java.util.List;

public class Paragraphe {
	
	private List <String> paragraphe;
	private int largeur;
	
	
	
	public Paragraphe () {
		paragraphe = new ArrayList<>();
	}
	
	
	
	public void fixeLargeur (int l) {
		largeur = l;
	}
	
	
	
	public void ajoute (String mot) {
		paragraphe.add(mot);
	}
	
	
	
	public int nbLignes () {
		int result = 1;
		int currentLineLength = 0;
		for (String mot : paragraphe) {
			if ((currentLineLength + mot.length()) > largeur) {
				result++;
				currentLineLength = mot.length();
			}
			else {
				currentLineLength += mot.length();
			}
			// Check to add space
			if (currentLineLength < largeur) {
				currentLineLength++;
			}
		}
		return result;
	}

	public String toString () {
		String result = "";
		int currentLineLength = 0;
		for (String mot : paragraphe) {
			if ((currentLineLength + mot.length()) > largeur) {
				result = result.concat("\n");
				currentLineLength = mot.length();
			}
			else {
				currentLineLength += mot.length();
			}
			result = result.concat(mot);
			// Check to add space
			if (currentLineLength < largeur) {
				currentLineLength++;
				result = result.concat(" ");
			}
		}
		return result;
	}
	
}
