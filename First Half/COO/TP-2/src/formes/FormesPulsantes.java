package formes;

import java.util.Random;

/**
 * Programme de base fournit pour le TP, se référer au TP pour les détails.
 * @author Guillaume Huard
 */
public class FormesPulsantes {
	final static int nbFormes = 8;
	final static int nbObjets = 15;
	final static int etapesPulsations = 20;
	final static int amplitudePulsation = 20;
	final static int delai = 100;

	static Forme creerForme(int type, MachineTrace m) {
		switch (type) {
		case 0:
			return new Carre(m);
		case 1:
			return new Triangle(m);
		case 2:
			return new Cercle(m);
		case 3:
			return new Losange(m);
		case 4:
			return new CarrePulsant(m);
		case 5:
			return new TrianglePulsant(m);
		case 6:
			return new CerclePulsant(m);
		case 7:
			return new LosangePulsant(m);
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
				if (f[i].getType() == "PULSANTE") {
					f[i].fixerTaille(tailles[i] + ((FormePulsante) f[i]).etapeSuivante());
				}
				else {
					f[i].fixerTaille(tailles[i]);
				}
				f[i].dessiner();
			}
			m.rafraichir();
			m.attendre(delai);
		}
	}
}
