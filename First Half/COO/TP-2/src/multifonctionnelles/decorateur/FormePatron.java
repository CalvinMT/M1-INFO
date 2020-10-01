package multifonctionnelles.decorateur;

public interface FormePatron {
	
	void fixerTaille(int t);
	
	void fixerPosition(int x, int y);

	int getXCentre();
	
	int getYCentre();
	
	int getTaille();
	
	void etapeSuivante();
	
	void dessiner();
	
}
