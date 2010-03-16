package practica1;

import ag.Seleccion;

public class ProblemaF1 implements agsimple.Problema {

	@Override
	public int tamCromosoma() {
		return (int) Math.ceil(Math.log(1.0 + (1.0 - 0) / 8.0) / Math.log(2));
	}

	@Override
	public int numMaxGen() {
		return 100;
	}

	@Override
	public double probCruce() {
		return 0.6;
	}

	@Override
	public double probMutacion() {
		return 0.2;
	}

	@Override
	public int tamPoblacion() {
		return 20;
	}

	@Override
	public void lanzar() {
		Poblacion p = new Poblacion(this);
		p.genPoblacionInicial();
		p.evaluarPoblacion();
		int gen = 0;
		while (gen < numMaxGen()) {
			Poblacion res = new Poblacion(this);
			Seleccion.ruleta(p, res);
			res.cruzar();
			res.mutar();
			res.evaluarPoblacion();
			p = res;
			++gen;
		}
		System.out.println("El mejor es " + p.getMejor().fenotipo());
	}

}
