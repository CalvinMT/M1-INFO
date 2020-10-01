package multifonctionnelles.decorateur;

import formes.MachineTrace;

public class Losange extends Forme {
	
	public Losange (MachineTrace m) {
		super(m);
	}
	
	
	
	@Override
	public void dessiner () {
		double x = xCentre;
		double y = yCentre + (taille / 3);
		machineTrace.placer(x, y);
		machineTrace.baisser();
		x = x - (Math.sqrt(Math.pow(taille, 2) - Math.pow(taille / 3, 2)));
		y = yCentre;
		machineTrace.placer(x, y);
		x = xCentre;
		y = yCentre - (taille / 3);
		machineTrace.placer(x, y);
		x = x + (Math.sqrt(Math.pow(taille, 2) - Math.pow(taille / 3, 2)));
		y = yCentre;
		machineTrace.placer(x, y);
		x = xCentre;
		y = y + (taille / 3);
		machineTrace.placer(x, y);
		machineTrace.lever();
	}
	
}
