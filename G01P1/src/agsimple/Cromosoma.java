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

package agsimple;

import ag.Problema;

public abstract class Cromosoma extends ag.Cromosoma {

	protected boolean m_cromosoma[] = null;

	public Cromosoma() {
		super();
		m_cromosoma = new boolean[Problema.self().tamCromosoma()];
	}

	public boolean[] cromosoma() {
		return m_cromosoma;
	}

	public void setCromosoma(boolean[] cromosoma) {
		m_cromosoma = cromosoma;
	}

}
