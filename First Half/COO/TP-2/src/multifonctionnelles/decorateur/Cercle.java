package multifonctionnelles.decorateur;

import formes.MachineTrace;

public class Cercle extends Forme {
	
	public Cercle (MachineTrace m) {
		super(m);
	}
	
	
	
	@Override
	public void dessiner () {
		int y = yCentre - (taille / 2);
		machineTrace.placer(xCentre, y);
		machineTrace.baisser();
		double circonference = Math.PI * taille;
		for (int i=0; i<360; i++) {
			machineTrace.tournerGauche(1);
			machineTrace.avancer(circonference/360);
		}
		machineTrace.lever();
	}
	
}
