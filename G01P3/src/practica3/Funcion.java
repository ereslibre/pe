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

import java.util.ArrayList;

public class Funcion {

	public static final int Not = 0;
	public static final int And = 1;
	public static final int Or  = 2;
	public static final int If  = 3;
	public static final int TotalFunciones = If;

	private int m_funcion;
	private int m_aridad;

	public Funcion(int funcion, int aridad) {
		m_funcion = funcion;
		m_aridad = aridad;
	}

	public int funcion() {
		return m_funcion;
	}

	public int aridad() {
		return m_aridad;
	}

	public static ArrayList<Termino> terminosControl() {
		ArrayList<Termino> res = new ArrayList<Termino>();
		res.add(new Termino("A0"));
		res.add(new Termino("A1"));
		return res;
	}

	public static ArrayList<Termino> terminosDatos() {
		ArrayList<Termino> res = new ArrayList<Termino>();
		res.add(new Termino("D0"));
		res.add(new Termino("D1"));
		res.add(new Termino("D2"));
		res.add(new Termino("D3"));
		return res;
	}
	
}
