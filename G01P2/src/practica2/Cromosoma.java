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

import ag.Cruce;

public class Cromosoma extends ag.Cromosoma {

	private int[] m_cromosoma;

	Cromosoma() {
		super();
	}

	@Override
	public double aptitud() {
		// TODO
		return 0;
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
		// TODO
		return null;
	}

	@Override
	public boolean esFactible() {
		for (int i = 0; i < m_cromosoma.length - 1; ++i) {
			for (int j = i + 1; j < m_cromosoma.length; ++j) {
				if (m_cromosoma[i] == m_cromosoma[j]) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public Object fenotipo() {
		// TODO
		return null;
	}

	@Override
	public Object genotipo() {
		// TODO: devolver genotipo
		return null;
	}

	@Override
	public void mutar() {
		// TODO
	}

	public int[] cromosoma() {
		return m_cromosoma;
	}

	public void setCromosoma(int[] cromosoma) {
		m_cromosoma = cromosoma;
	}

}
