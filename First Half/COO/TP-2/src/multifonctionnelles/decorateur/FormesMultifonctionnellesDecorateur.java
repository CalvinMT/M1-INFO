package multifonctionnelles.decorateur;

import java.util.Random;

import formes.MachineTrace;
import multifonctionnelles.decorateur.decoration.Clignotement;
import multifonctionnelles.decorateur.decoration.Pulsation;
import multifonctionnelles.decorateur.decoration.Rotation;

/**
 * Programme de base fournit pour le TP, se référer au TP pour les détails.
 * @author Guillaume Huard
 */
public class FormesMultifonctionnellesDecorateur {
	final static int nbFormes = 4;
	final static int nbObjets = 15;
	final static int delai = 100;

	static FormePatron creerForme(int type, MachineTrace m) {
		FormePatron forme;
		switch (type) {
			case 0:
				forme = new Carre(m);
				break;
			case 1:
				forme = new Triangle(m);
				break;
			case 2:
				forme = new Cercle(m);
				break;
			case 3:
				forme = new Losange(m);
				break;
			default:
				throw new RuntimeException("Forme Inconnue");
		}
		Random r = new Random();
		if (r.nextBoolean()) {
			forme = new Pulsation(forme);
		}
		if (r.nextBoolean()) {
			forme = new Rotation(forme);
		}
		if (r.nextBoolean()) {
			forme = new Clignotement(forme);
		}
		return forme;
	}

	public static void main(String[] args) {
		MachineTrace m;
		FormePatron[] f;
		int[] tailles;
		Random r;

		m = new MachineTrace(400, 400);
		m.masquerPointeur();
		m.rafraichissementAutomatique(false);

		f = new FormePatron[nbObjets];
		tailles = new int[f.length];
		r = new Random();
		for (int i = 0; i < f.length; i++) {
			f[i] = creerForme(r.nextInt(nbFormes), m);
			f[i].fixerPosition(r.nextInt(200) - 100, r.nextInt(200) - 100);
			tailles[i] = r.nextInt(20) + 15;
		}

		while (true) {
			m.effacerTout();
			for (int i = 0; i < f.length; i++) {
				f[i].fixerTaille(tailles[i]);
				f[i].etapeSuivante();
				f[i].dessiner();
			}
			m.rafraichir();
			m.attendre(delai);
		}
	}
}
