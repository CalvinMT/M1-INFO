package multifonctionnelles;

import formes.MachineTrace;

public class Triangle extends Forme {
	
	public Triangle (MachineTrace m, boolean p, boolean r, boolean c) {
		super(m, p, r, c);
	}
	
	
	
	@Override
	public void dessiner () {
		double y = yCentre + (taille / 2);
		machineTrace.placer(xCentre, y);
		machineTrace.baisser();
		machineTrace.avancer(taille);
		machineTrace.tournerGauche(120);
		machineTrace.avancer(taille);
		machineTrace.tournerGauche(120);
		machineTrace.avancer(taille);
		machineTrace.tournerGauche(120);
		machineTrace.avancer(taille);
		machineTrace.lever();
	}
	
}
