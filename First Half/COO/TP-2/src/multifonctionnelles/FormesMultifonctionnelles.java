package multifonctionnelles;

import java.util.Random;

import formes.MachineTrace;

/**
 * Programme de base fournit pour le TP, se référer au TP pour les détails.
 * @author Guillaume Huard
 */
public class FormesMultifonctionnelles {
	final static int nbFormes = 4;
	final static int nbObjets = 15;
	final static int etapesPulsations = 20;
	final static int etapesRotation = 60;
	final static int amplitudePulsation = 20;
	final static int delai = 100;

	static Forme creerForme(int type, MachineTrace m) {
		Random r = new Random();
		switch (type) {
		case 0:
			return new Carre(m, r.nextBoolean(), r.nextBoolean(), r.nextBoolean());
		case 1:
			return new Triangle(m, r.nextBoolean(), r.nextBoolean(), r.nextBoolean());
		case 2:
			return new Cercle(m, r.nextBoolean(), r.nextBoolean(), r.nextBoolean());
		case 3:
			return new Losange(m, r.nextBoolean(), r.nextBoolean(), r.nextBoolean());
		default:
			throw new RuntimeException("Forme Inconnue");
		}
	}

	public static void main(String[] args) {
		MachineTrace m;
		Forme[] f;
		int[] tailles;
		Random r;

		m = new MachineTrace(400, 400);
		m.masquerPointeur();
		m.rafraichissementAutomatique(false);

		f = new Forme[nbObjets];
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
				if (f[i].estPulsante()) {
					f[i].fixerTaille(tailles[i] + f[i].etapePulsanteSuivante());
				}
				else {
					f[i].fixerTaille(tailles[i]);
				}
				if (f[i].estRotative()) {
					f[i].etapeRotativeSuivante();
				}
				if (! f[i].estClignotante()  ||  (f[i].estClignotante()  &&  f[i].etapeClignotanteSuivante())) {
					f[i].dessiner();
				}
			}
			m.rafraichir();
			m.attendre(delai);
		}
	}
}
