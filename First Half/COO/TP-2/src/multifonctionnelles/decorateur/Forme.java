package multifonctionnelles.decorateur;

import formes.MachineTrace;

public abstract class Forme implements FormePatron {
	
	protected MachineTrace machineTrace;
	
	protected int xCentre = 0;
	protected int yCentre = 0;
	
	protected int taille = 0;
	
	
	
	public Forme (MachineTrace m) {
		machineTrace = m;
	}
	
	
	
	@Override
	public void fixerPosition (int x, int y) {
		xCentre = x;
		yCentre = y;
	}
	
	@Override
	public void fixerTaille (int t) {
		taille = t;
	}
	
	@Override
	public int getXCentre () {
		return xCentre;
	}
	
	@Override
	public int getYCentre () {
		return yCentre;
	}
	
	@Override
	public int getTaille () {
		return taille;
	}
	
	@Override
	public void etapeSuivante () {
		return;
	}
	
	@Override
	public void dessiner () {
		return;
	}
	
}
