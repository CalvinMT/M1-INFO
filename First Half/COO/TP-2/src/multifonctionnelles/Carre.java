package multifonctionnelles;

import formes.MachineTrace;

public class Carre extends Forme {
	
	public Carre (MachineTrace m, boolean p, boolean r, boolean c) {
		super(m, p, r, c);
	}
	
	
	
	@Override
	public void dessiner () {
		double x = xCentre - (taille / 2);
		double y = yCentre + (taille / 2);
		machineTrace.placer(rotationX(x, y), rotationY(x, y));
		machineTrace.baisser();
		y -= taille;
		machineTrace.placer(rotationX(x, y), rotationY(x, y));
		x += taille;
		machineTrace.placer(rotationX(x, y), rotationY(x, y));
		y += taille;
		machineTrace.placer(rotationX(x, y), rotationY(x, y));
		x -= taille;
		machineTrace.placer(rotationX(x, y), rotationY(x, y));
		machineTrace.lever();
	}
	
}
