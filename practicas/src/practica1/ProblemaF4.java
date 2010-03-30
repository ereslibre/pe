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

import ag.Poblacion;

public class ProblemaF4 extends agsimple.Problema {

	@Override
	public int tamCromosoma() {
		return (int) Math.ceil(Math.log(1.0 + 20.0 / precision()) / Math.log(2)) * 2;
	}

	@Override
	protected Poblacion genPoblacionVacia() {
		return new PoblacionF4(this);
	}

}
