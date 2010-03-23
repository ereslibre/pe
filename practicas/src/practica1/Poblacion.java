package practica1;

import agsimple.Problema;

public class Poblacion extends agsimple.Poblacion {

	public Poblacion(Problema problema) {
		super(problema);
	}

	@Override
	public Cromosoma generaCromosoma() {
		return new Cromosoma(this);
	}

}
