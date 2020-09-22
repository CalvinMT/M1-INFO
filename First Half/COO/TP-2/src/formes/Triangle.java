package formes;

public class Triangle extends Forme {
	
	public Triangle (MachineTrace m) {
		super(m);
	}
	
	
	
	@Override
	public void dessiner () {
		double hauteur = Math.sqrt(3/2) * taille;
		double y = yCentre - hauteur;
		machineTrace.placer(xCentre, y);
		machineTrace.baisser();
		machineTrace.tournerGauche(120);
		machineTrace.avancer(taille);
		machineTrace.tournerGauche(120);
		machineTrace.avancer(taille);
		machineTrace.tournerGauche(120);
		machineTrace.avancer(taille);
		machineTrace.lever();
	}
	
}
