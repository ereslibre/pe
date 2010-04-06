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

import java.util.ArrayList;
import java.util.Iterator;

import ag.Cruce;

public class CromosomaF5 extends agsimple.Cromosoma {

	CromosomaF5(PoblacionF5 poblacion) {
		super(poblacion);
	}

	@SuppressWarnings("unchecked")
	@Override
	public double aptitud() {
		Double res = 0.0;
		Iterator<Double> it = ((ArrayList<Double>) fenotipo()).iterator();
		int i = 1;
		while (it.hasNext()) {
			final Double xi = it.next();
			res += Math.sin(xi) * Math.pow(Math.sin((((double) i + 1.0)) * Math.pow(xi, 2) / Math.PI), 20);
			++i;
		}
		return (poblacion().evaluacionMaxima() - (-res)) + Math.abs(poblacion().evaluacionMinima());
	}

	@Override
	public Object clone() {
		CromosomaF5 res = new CromosomaF5((PoblacionF5) m_poblacion);
		res.m_madre = m_madre;
		res.m_padre = m_padre;
		res.m_cromosoma = new boolean[m_cromosoma.length];
		for (int i = 0; i < m_cromosoma.length; ++i) {
			res.m_cromosoma[i] = m_cromosoma[i];
		}
		return res;
	}

	@Override
	public Cruce cruzar(ag.Cromosoma cromosoma) {
		final int tamCromosoma = ((agsimple.Problema) poblacion().problema()).tamCromosoma();
		int posCruce = (int) ((Math.random() * (double) (tamCromosoma - 1.0)));

		CromosomaF5 hijo1 = (CromosomaF5) poblacion().genCromosomaVacio();
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

		CromosomaF5 hijo2 = (CromosomaF5) poblacion().genCromosomaVacio();
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

	@SuppressWarnings("unchecked")
	@Override
	public boolean esFactible() {
		Iterator<Double> it = ((ArrayList<Double>) fenotipo()).iterator();
		while (it.hasNext()) {
			final Double xi = it.next();
			if (xi < 0 || xi > Math.PI) {
				return false;
			}
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public double evaluacion() {
		Double res = 0.0;
		Iterator<Double> it = ((ArrayList<Double>) fenotipo()).iterator();
		int i = 1;
		while (it.hasNext()) {
			final Double xi = it.next();
			res += Math.sin(xi) * Math.pow(Math.sin((((double) i + 1.0)) * Math.pow(xi, 2) / Math.PI), 20);
			++i;
		}
		return -res;
	}

	@Override
	public Object fenotipo() {
		ArrayList<Double> res = new ArrayList<Double>();
		final int tamCromosoma = ((agsimple.Problema) poblacion().problema()).tamCromosoma();
		final int tamGen = (int) (Math.log(1.0 + Math.PI / poblacion().problema().precision()) / Math.log(2));
		for (int i = 0; i < tamCromosoma / tamGen; ++i) {
			boolean[] gen = new boolean[tamGen];
			for (int j = 0; j < tamGen; ++j) {
				gen[j] = m_cromosoma[(i * tamGen) + j];
			}
			res.add(bin2dec(gen) * (Math.PI / (Math.pow(2, tamGen) - 1.0)));
		}

		return res;
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
		for (int i = 0; i < ((agsimple.Problema) poblacion().problema()).tamCromosoma(); ++i) {
			if (Math.random() < poblacion().problema().probMutacion()) {
				genotipo[i] = !genotipo[i];
			}
		}
	}

}
