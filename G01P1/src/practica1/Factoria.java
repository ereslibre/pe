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
import agsimple.Cromosoma;

public class Factoria {

	public static final int Funcion1 = 0;
	public static final int Funcion2 = 1;
	public static final int Funcion3 = 2;
	public static final int Funcion4 = 3;
	public static final int Funcion5 = 4;

	public static Poblacion genPoblacionVacia() {
		return new agsimple.Poblacion();
	}

	public static Cromosoma generaCromosoma(Poblacion poblacion, int funcion) {
		Cromosoma res = null;
		switch (funcion) {
			case Funcion1:
				res = new CromosomaF1();
				break;
			case Funcion2:
				res = new CromosomaF2();
				break;
			case Funcion3:
				res = new CromosomaF3();
				break;
			case Funcion4:
				res = new CromosomaF4();
				break;
			case Funcion5:
				res = new CromosomaF5();
				break;
			default:
				break;
		}
		res.setPoblacion(poblacion);
		return res;
	}

}
