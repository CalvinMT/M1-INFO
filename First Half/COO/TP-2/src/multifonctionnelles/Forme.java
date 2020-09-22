package multifonctionnelles;

import formes.MachineTrace;

public abstract class Forme {
	
	protected MachineTrace machineTrace;
	
	protected int xCentre = 0;
	protected int yCentre = 0;
	
	protected int taille = 0;
	
	protected boolean pulsante = false;
	protected boolean rotative = false;
	protected boolean clignotante = false;

	private int etapePulsante = 0;
	private int etapeRotative = 0;
	private boolean etapeClignotante = true;
	
	
	
	public Forme (MachineTrace m, boolean p, boolean r, boolean c) {
		machineTrace = m;
		pulsante = p;
		rotative = r;
		clignotante = c;
	}
	
	
	
	public void fixerPosition (int x, int y) {
		xCentre = x;
		yCentre = y;
	}
	
	public void fixerTaille (int t) {
		taille = t;
	}
	

	
	public boolean estPulsante () {
		return pulsante;
	}
	
	public boolean estRotative () {
		return rotative;
	}
	
	public boolean estClignotante () {
		return clignotante;
	}
	

	
	public int etapePulsanteSuivante () {
		etapePulsante++;
		if (etapePulsante > FormesMultifonctionnelles.etapesPulsations) {
			etapePulsante = 0;
		}
		return (int) (FormesMultifonctionnelles.amplitudePulsation * (Math.sin(etapePulsante * 2 * Math.PI / FormesMultifonctionnelles.etapesPulsations) + 1) / 2);
	}
	
	public double etapeRotativeSuivante () {
		etapeRotative++;
		if (etapeRotative > FormesMultifonctionnelles.etapesRotation) {
			etapeRotative = 0;
		}
		// TODO
		return 0;
	}
	
	public boolean etapeClignotanteSuivante () {
		etapeClignotante = ! etapeClignotante;
		return etapeClignotante;
	}
	
	public abstract void dessiner ();
	
}
