package formes;

public class Carre extends Forme {
	
	public Carre (MachineTrace m) {
		super(m);
	}
	
	
	
	@Override
	public void dessiner () {
		int x = xCentre - (taille / 2);
		int y = yCentre + (taille / 2);
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
