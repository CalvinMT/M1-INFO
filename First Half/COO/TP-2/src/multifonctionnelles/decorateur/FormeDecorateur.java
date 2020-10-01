package multifonctionnelles.decorateur;

public class FormeDecorateur implements FormePatron {
	
	private FormePatron forme;
	
	
	
	public FormeDecorateur (FormePatron f) {
		forme = f;
	}
	
	
	
	@Override
	public void fixerTaille(int t) {
		forme.fixerTaille(t);
	}
	
	@Override
	public void fixerPosition(int x, int y) {
		forme.fixerPosition(x, y);
	}
	
	@Override
	public int getXCentre () {
		return forme.getXCentre();
	}
	
	@Override
	public int getYCentre () {
		return forme.getYCentre();
	}
	
	@Override
	public int getTaille () {
		return forme.getTaille();
	}
	
	@Override
	public void etapeSuivante () {
		forme.etapeSuivante();
	}
	
	@Override
	public void dessiner() {
		forme.dessiner();
	}
	
}
