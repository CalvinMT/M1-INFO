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
		machineTrace.orienter(-60);
		machineTrace.avancer(taille);
		machineTrace.orienter(-180);
		machineTrace.avancer(taille);
		machineTrace.orienter(-300);
		machineTrace.avancer(taille);
		machineTrace.lever();
	}
	
}
