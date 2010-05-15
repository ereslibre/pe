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

package practica3;

import ag.Cruce;
import ag.Problema;

public class Cromosoma extends ag.Cromosoma {

	private int[]  m_cromosoma = null;

	Cromosoma() {
		super();
		m_cromosoma = new int[Problema.self().tamCromosoma()];
	}

	@Override
	public double aptitud() {
		return 0;
	}

	@Override
	public double evaluacion() {
		return 0;
	}

	@Override
	public Object clone() {
		return null;
	}

	@Override
	public Cruce cruzar(ag.Cromosoma cromosoma) {
		return null;
	}

	@Override
	public boolean esFactible() {
		return true;
	}

	@Override
	public Object fenotipo() {
		return null;
	}

	@Override
	public Object genotipo() {
		return m_cromosoma;
	}

	@Override
	public void mutar() {
	}

	public int[] cromosoma() {
		return m_cromosoma;
	}

	public void setCromosoma(int[] cromosoma) {
		m_cromosoma = cromosoma;
	}

}
