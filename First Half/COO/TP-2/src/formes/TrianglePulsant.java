package formes;

public class TrianglePulsant extends FormePulsante {
	
	public TrianglePulsant (MachineTrace m) {
		super(m);
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
