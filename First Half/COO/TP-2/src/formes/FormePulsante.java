package formes;

public abstract class FormePulsante extends Forme {
	
	private int etape = 0;
	private int amplitudePulsation = 20;
	
	
	
	public FormePulsante (MachineTrace m) {
		super(m);
	}
	
	
	
	public int etapeSuivante () {
		etape++;
		if (etape > FormesPulsantes.etapesPulsations) {
			etape = 0;
		}
		return (int) (amplitudePulsation * (Math.sin(etape * 2 * Math.PI / FormesPulsantes.etapesPulsations) + 1) / 2);
	}
	
	
	
	@Override
	public String getType () {
		return "PULSANTE";
	}
	
}
