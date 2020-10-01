package multifonctionnelles.decorateur;

import formes.MachineTrace;

public class Carre extends Forme {
	
	public Carre (MachineTrace m) {
		super(m);
	}
	
	
	
	@Override
	public void dessiner () {
		double x = xCentre - (taille / 2);
		double y = yCentre + (taille / 2);
		machineTrace.placer(x, y);
		machineTrace.baisser();
		y -= taille;
		machineTrace.placer(x, y);
		x += taille;
		machineTrace.placer(x, y);
		y += taille;
		machineTrace.placer(x, y);
		x -= taille;
		machineTrace.placer(x, y);
		machineTrace.lever();
	}
	
}
