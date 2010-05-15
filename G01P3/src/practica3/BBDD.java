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

public class BBDD {

	public static boolean test(boolean a0, boolean a1, boolean d0, boolean d1, boolean d2, boolean d3, boolean res) {
		if (a0 && a1) {
			return d3 == res;
		} else if (a0) {
			return d1 == res;
		} else if (a1) {
			return d2 == res;
		}
		return d0 == res;
	}

}
