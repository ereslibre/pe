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

package maxfuncion;

public class Problema implements agsimple.Problema {

	@Override
	public int tamCromosoma() {
		return 5;
	}

	@Override
	public int numMaxGen() {
		return 10;
	}

	@Override
	public double probCruce() {
		return 0.7;
	}

	@Override
	public double probMutacion() {
		return 0.2;
	}

	@Override
	public int tamPoblacion() {
		return 100;
	}

}
