package multifonctionnelles.decorateur.decoration;

import multifonctionnelles.decorateur.FormeDecorateur;
import multifonctionnelles.decorateur.FormePatron;

public class Clignotement extends FormeDecorateur {
	
	private boolean etapeClignotante = true;
	
	
	
	public Clignotement(FormePatron f) {
		super(f);
	}
	
	
	
	public void etapeClignotanteSuivante () {
		etapeClignotante = ! etapeClignotante;
	}
	
	
	
	@Override
	public void etapeSuivante () {
		etapeClignotanteSuivante();
		super.etapeSuivante();
	}
	
	@Override
	public void dessiner () {
		if (etapeClignotante) {
			super.dessiner();
		}
	}
	
}
