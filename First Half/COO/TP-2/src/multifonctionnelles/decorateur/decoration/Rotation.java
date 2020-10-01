package multifonctionnelles.decorateur.decoration;

import multifonctionnelles.decorateur.FormeDecorateur;
import multifonctionnelles.decorateur.FormePatron;

public class Rotation extends FormeDecorateur {
	
	private final int MAX_ETAPE = 60;

	private double angleRotation = 0;
	private int etapeRotative = 0;
	
	
	
	public Rotation(FormePatron f) {
		super(f);
	}
	
	
	
	public void etapeRotativeSuivante () {
		etapeRotative++;
		if (etapeRotative > MAX_ETAPE) {
			etapeRotative = 0;
		}
		angleRotation = etapeRotative * 360 / MAX_ETAPE;
	}
	
	protected double rotationX (double x, double y) {
		x = x - getXCentre();
		y = y - getYCentre();
		x = x * Math.cos(angleRotation) - y * Math.sin(angleRotation);
		return x + getXCentre();
	}
	
	protected double rotationY (double x, double y) {
		x = x - getXCentre();
		y = y - getYCentre();
		y = x * Math.sin(angleRotation) + y * Math.cos(angleRotation);
		return y + getYCentre();
	}
	
	
	
	@Override
	public void etapeSuivante () {
		super.etapeSuivante();
		etapeRotativeSuivante();
	}
	
}
