package multifonctionnelles;

import formes.MachineTrace;

public abstract class Forme {
	
	protected MachineTrace machineTrace;
	
	protected int xCentre = 0;
	protected int yCentre = 0;
	
	protected int taille = 0;
	protected double angleRotation = 0;
	
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
	
	public void etapeRotativeSuivante () {
		etapeRotative++;
		if (etapeRotative > FormesMultifonctionnelles.etapesRotation) {
			etapeRotative = 0;
		}
		angleRotation = etapeRotative * 360 / FormesMultifonctionnelles.etapesRotation;
	}
	
	protected int rotationX (int x, int y) {
		x = x - xCentre;
		x = (int) (x * Math.cos(angleRotation) - y * Math.sin(angleRotation));
		return x + xCentre;
	}
	
	protected int rotationY (int x, int y) {
		y = y - yCentre;
		y = (int) (x * Math.sin(angleRotation) + y * Math.cos(angleRotation));
		return y + yCentre;
	}
	
	public boolean etapeClignotanteSuivante () {
		etapeClignotante = ! etapeClignotante;
		return etapeClignotante;
	}
	
	public abstract void dessiner ();
	
}
