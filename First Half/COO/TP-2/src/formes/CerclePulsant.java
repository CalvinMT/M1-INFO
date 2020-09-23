package formes;

public class CerclePulsant extends FormePulsante {
	
	public CerclePulsant (MachineTrace m) {
		super(m);
	}
	
	
	
	@Override
	public void dessiner () {
		double y = yCentre + (taille / 2);
		machineTrace.placer(xCentre, y);
		machineTrace.baisser();
		machineTrace.orienter(180);
		double circonference = Math.PI * taille;
		for (int i=0; i<360; i++) {
			machineTrace.avancer(circonference/360);
			machineTrace.tournerGauche(1);
		}
		machineTrace.lever();
	}
	
}
