package document;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Entete {
	
	private Texte titre;
	private Texte auteur;
	private Texte date;
	

	
	public Entete (String t, String a) {
		this(t, a, new Date());
	}
	
	public Entete (String t, String a, Date d) {
		titre = new TexteCentre(t);
		auteur = new TexteCentre(a);
		date = new TexteCentre(new SimpleDateFormat("dd-MM-yyyy").format(d));
		fixeLargeurDefaut();
	}
	
	
	
	private void fixeLargeurDefaut () {
		int l = titre.texte().length();
		if (l < auteur.texte().length()) {
			l = auteur.texte().length();
		}
		if (l < date.texte().length()) {
			l = date.texte().length();
		}
		fixeLargeur(l);
	}
	
	public void fixeLargeur (int l) {
		if (l >= titre.texte().length()  &&  l >= auteur.texte().length()  &&  l >= date.texte().length()) {
			titre.fixeLargeur(l);
			auteur.fixeLargeur(l);
			date.fixeLargeur(l);
		}
		else {
			fixeLargeurDefaut();
		}
	}
	
	
	
	public String toString () {
		String result = "";
		result = result.concat(titre.toString());
		result = result.concat("\n");
		result = result.concat(auteur.toString());
		result = result.concat("\n");
		result = result.concat(date.toString());
		return result;
	}
	
}
