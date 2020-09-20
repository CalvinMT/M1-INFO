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
	
	
	
	// FIXME - doit prendre en compte les espaces
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
		}
		return result;
	}

	// FIXME - doit prendre en compte les espaces
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
		}
		return result;
	}
	
}
