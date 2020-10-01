package multifonctionnelles.decorateur.decoration;

import multifonctionnelles.decorateur.FormeDecorateur;
import multifonctionnelles.decorateur.FormePatron;

public class Pulsation extends FormeDecorateur {
	
	private final int MAX_ETAPE = 20;
	private final int AMPLITUDE = 20;

	private int etapePulsante = 0;
	
	
	
	public Pulsation (FormePatron f) {
		super(f);
	}
	

	
	public int etapePulsanteSuivante () {
		etapePulsante++;
		if (etapePulsante > MAX_ETAPE) {
			etapePulsante = 0;
		}
		return (int) (AMPLITUDE * (Math.sin(etapePulsante * 2 * Math.PI / MAX_ETAPE) + 1) / 2);
	}
	
	
	
	@Override
	public void fixerTaille (int t) {
		super.fixerTaille(t);
	}
	
	@Override
	public void etapeSuivante () {
		super.etapeSuivante();
		fixerTaille(getTaille() + etapePulsanteSuivante());
	}
	
}
