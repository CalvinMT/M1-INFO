package formes;

public abstract class Forme {
	
	protected MachineTrace machineTrace;
	
	protected int xCentre = 0;
	protected int yCentre = 0;
	
	protected int taille = 0;
	
	
	
	public Forme (MachineTrace m) {
		machineTrace = m;
	}
	
	
	
	public void fixerPosition (int x, int y) {
		xCentre = x;
		yCentre = y;
	}
	
	public void fixerTaille (int t) {
		taille = t;
	}
	
	
	
	public String getType () {
		return "STATIQUE";
	}
	
	
	
	public abstract void dessiner ();
	
}
