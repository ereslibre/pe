package practica1;

import ag.Cruce;

public class Cromosoma extends agsimple.Cromosoma {

	@Override
	public double aptitud() {
		Double x = (Double) fenotipo();
		return x + Math.abs(Math.sin(32.0 * Math.PI * x));
	}

	@Override
	public Object clone() {
		Cromosoma res = new Cromosoma();
		res.m_madre = m_madre;
		res.m_padre = m_padre;
		res.m_poblacion = m_poblacion;
		res.m_cromosoma = new boolean[m_cromosoma.length];
		for (int i = 0; i < m_cromosoma.length; ++i) {
			res.m_cromosoma[i] = m_cromosoma[i];
		}
		return res;
	}

	@Override
	public Cruce cruzar(ag.Cromosoma cromosoma) {
		final int tamCromosoma = ((ProblemaF1) poblacion().problema()).tamCromosoma();
		int posCruce = (int) Math.random() * tamCromosoma - 1;

		Cromosoma hijo1 = (Cromosoma) poblacion().genCromosomaVacio();
		boolean hijo1c[] = new boolean[tamCromosoma];
		for (int i = 0; i < tamCromosoma; ++i) {
			if (i <= posCruce) {
				hijo1c[i] = ((boolean[]) genotipo())[i];
			} else {
				hijo1c[i] = ((boolean[]) cromosoma.genotipo())[i];
			}
		}
		hijo1.setCromosoma(hijo1c);
		hijo1.setMadre(this);
		hijo1.setPadre(cromosoma);

		Cromosoma hijo2 = (Cromosoma) poblacion().genCromosomaVacio();
		boolean hijo2c[] = new boolean[tamCromosoma];
		for (int i = 0; i < tamCromosoma; ++i) {
			if (i <= posCruce) {
				hijo2c[i] = ((boolean[]) cromosoma.genotipo())[i];
			} else {
				hijo2c[i] = ((boolean[]) genotipo())[i];
			}
		}
		hijo2.setCromosoma(hijo1c);
		hijo2.setMadre(this);
		hijo2.setPadre(cromosoma);

		return new Cruce(hijo1, hijo2);
	}

	@Override
	public boolean esFactible() {
		Double x = (Double) fenotipo();
		return x >= 0 && x <= 1;
	}

	@Override
	public double evaluacion() {
		Double x = (Double) fenotipo();
		return x + Math.abs(Math.sin(32.0 * Math.PI * x));
	}

	@Override
	public Object fenotipo() {
		final int tamCromosoma = ((ProblemaF1) poblacion().problema()).tamCromosoma();
		return bin2dec(m_cromosoma) * (1.0 / (Math.pow(2, tamCromosoma) - 1));
	}

	public Double bin2dec(boolean[] m_cromosoma) {
		Double res = 0.0;
		for (int i = 0; i < m_cromosoma.length; ++i) {
			res += m_cromosoma[i] ? Math.pow(2, i) : 0;
		}
		return res;
	}

	@Override
	public Object genotipo() {
		return m_cromosoma;
	}

	@Override
	public void mutar() {
		boolean genotipo[] = (boolean[]) genotipo();
		for (int i = 0; i < ((ProblemaF1) poblacion().problema()).tamCromosoma(); ++i) {
			if (Math.random() < poblacion().problema().probMutacion()) {
				genotipo[i] = !genotipo[i];
			}
		}
	}

}
