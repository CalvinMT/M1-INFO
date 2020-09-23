package multifonctionnelles;

import formes.MachineTrace;

public class Losange extends Forme {
	
	public Losange (MachineTrace m, boolean p, boolean r, boolean c) {
		super(m, p, r, c);
	}
	
	
	
	@Override
	public void dessiner () {
		double x = xCentre;
		double y = yCentre + (taille / 3);
		machineTrace.placer(rotationX(x, y), rotationY(x, y));
		machineTrace.baisser();
		x = x - (Math.sqrt(Math.pow(taille, 2) - Math.pow(taille / 3, 2)));
		y = yCentre;
		machineTrace.placer(rotationX(x, y), rotationY(x, y));
		x = xCentre;
		y = yCentre - (taille / 3);
		machineTrace.placer(rotationX(x, y), rotationY(x, y));
		x = x + (Math.sqrt(Math.pow(taille, 2) - Math.pow(taille / 3, 2)));
		y = yCentre;
		machineTrace.placer(rotationX(x, y), rotationY(x, y));
		x = xCentre;
		y = y + (taille / 3);
		machineTrace.placer(rotationX(x, y), rotationY(x, y));
		machineTrace.lever();
	}
	
}
