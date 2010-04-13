/**
 * Copyright 2010 Rafael Fernández López <ereslibre@ereslibre.es>
 * Copyright 2010 Ángel Valero Picazo <valeropc@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package practica1;

import agsimple.Cromosoma;
import ag.Cruce;
import ag.Problema;

public class CromosomaF1 extends agsimple.Cromosoma {

	CromosomaF1() {
		super();
	}

	@Override
	public double aptitud() {
		Double x = (Double) fenotipo();
		return x + Math.abs(Math.sin(32.0 * Math.PI * x));
	}

	@Override
	public Object clone() {
		CromosomaF1 res = new CromosomaF1();
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
		final int tamCromosoma = Problema.self().tamCromosoma();
		int posCruce = (int) ((Math.random() * ((double) tamCromosoma - 1.0)));

		Cromosoma hijo1 = Factoria.generaCromosoma(m_poblacion, Factoria.Funcion1);
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

		Cromosoma hijo2 = Factoria.generaCromosoma(m_poblacion, Factoria.Funcion1);
		boolean hijo2c[] = new boolean[tamCromosoma];
		for (int i = 0; i < tamCromosoma; ++i) {
			if (i <= posCruce) {
				hijo2c[i] = ((boolean[]) cromosoma.genotipo())[i];
			} else {
				hijo2c[i] = ((boolean[]) genotipo())[i];
			}
		}
		hijo2.setCromosoma(hijo2c);
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
		final int tamCromosoma = Problema.self().tamCromosoma();
		return bin2dec(m_cromosoma) * (1.0 / (Math.pow(2, tamCromosoma) - 1.0));
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
		for (int i = 0; i < Problema.self().tamCromosoma(); ++i) {
			if (Math.random() < Problema.self().probMutacion()) {
				genotipo[i] = !genotipo[i];
			}
		}
	}

}
