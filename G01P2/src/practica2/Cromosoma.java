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

package practica2;

import java.util.ArrayList;

import ag.Cruce;
import ag.Problema;

public class Cromosoma extends ag.Cromosoma {

	private int[] m_cromosoma = null;

	Cromosoma() {
		super();
		m_cromosoma = new int[Problema.self().tamCromosoma()];
	}

	@Override
	public double aptitud() {
		double res = 0;
		for (int i = 0; i < m_cromosoma.length - 1; ++i) {
			res += Utilidades.getDist(m_cromosoma[i], m_cromosoma[i + 1]);
		}
		res += Utilidades.getDist(m_cromosoma[m_cromosoma.length - 1], m_cromosoma[0]);
		return 10000000 - res;
	}

	@Override
	public double evaluacion() {
		double res = 0;
		for (int i = 0; i < m_cromosoma.length - 1; ++i) {
			res += Utilidades.getDist(m_cromosoma[i], m_cromosoma[i + 1]);
		}
		res += Utilidades.getDist(m_cromosoma[m_cromosoma.length - 1], m_cromosoma[0]);
		return res;
	}

	@Override
	public Object clone() {
		Cromosoma res = new Cromosoma();
		res.m_madre = m_madre;
		res.m_padre = m_padre;
		res.m_poblacion = m_poblacion;
		for (int i = 0; i < m_cromosoma.length; ++i) {
			res.m_cromosoma[i] = m_cromosoma[i];
		}
		return res;
	}

	@Override
	public Cruce cruzar(ag.Cromosoma cromosoma) {
		final int tamCromosoma = Problema.self().tamCromosoma();
		int posCruce = (int) ((Math.random() * ((double) tamCromosoma - 1.0)));

		Cromosoma hijo1 = Factoria.generaCromosoma(m_poblacion);
		int hijo1c[] = new int[tamCromosoma];
		for (int i = 0; i < tamCromosoma; ++i) {
			if (i <= posCruce) {
				hijo1c[i] = ((int[]) genotipo())[i];
			} else {
				hijo1c[i] = ((int[]) cromosoma.genotipo())[i];
			}
		}
		hijo1.setCromosoma(hijo1c);
		hijo1.setMadre(this);
		hijo1.setPadre(cromosoma);

		Cromosoma hijo2 = Factoria.generaCromosoma(m_poblacion);
		int hijo2c[] = new int[tamCromosoma];
		for (int i = 0; i < tamCromosoma; ++i) {
			if (i <= posCruce) {
				hijo2c[i] = ((int[]) cromosoma.genotipo())[i];
			} else {
				hijo2c[i] = ((int[]) genotipo())[i];
			}
		}
		hijo2.setCromosoma(hijo2c);
		hijo2.setMadre(this);
		hijo2.setPadre(cromosoma);

		return new Cruce(hijo1, hijo2);
	}

	@Override
	public boolean esFactible() {
		return true;
//		for (int i = 0; i < m_cromosoma.length - 1; ++i) {
//			for (int j = i + 1; j < m_cromosoma.length; ++j) {
//				if (m_cromosoma[i] == m_cromosoma[j]) {
//					return false;
//				}
//			}
//		}
//		return true;
	}

	@Override
	public Object fenotipo() {
		ArrayList<String> res = new ArrayList<String>();
		for (int i = 0; i < m_cromosoma.length; ++i) {
			res.add(Utilidades.getCiudad(m_cromosoma[i]));
		}
		return res;
	}

	@Override
	public Object genotipo() {
		return m_cromosoma;
	}

	@Override
	public void mutar() {
		for (int i = 0; i < Problema.self().tamCromosoma(); ++i) {
			if (Math.random() < Problema.self().probMutacion()) {
				m_cromosoma[i] = (int) ((Problema.self().tamCromosoma() - 1) * Math.random());
			}
		}
	}

	public int[] cromosoma() {
		return m_cromosoma;
	}

	public void setCromosoma(int[] cromosoma) {
		m_cromosoma = cromosoma;
	}

}
