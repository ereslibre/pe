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

import java.util.ListIterator;

import ag.Problema;

import practica1.Factoria;

public class Poblacion extends ag.Poblacion {

	public Poblacion() {
		super();
	}

	@Override
	public void anadeCromosomaAleatorio() {
		Cromosoma res = null;
		switch (Problema.self().ventanaPrincipal().problemaSeleccionado()) {
			case 0:
				res = Factoria.generaCromosoma(this, Factoria.Funcion1);
				break;
			case 1:
				res = Factoria.generaCromosoma(this, Factoria.Funcion2);
				break;
			case 2:
				res = Factoria.generaCromosoma(this, Factoria.Funcion3);
				break;
			case 3:
				res = Factoria.generaCromosoma(this, Factoria.Funcion4);
				break;
			case 4:
				res = Factoria.generaCromosoma(this, Factoria.Funcion5);
				break;
			default:
				break;
		}
		boolean cromosomaFactible = false;
		while (!cromosomaFactible) {
			boolean cromosoma[] = res.cromosoma();
			for (int i = 0; i < Problema.self().tamCromosoma(); ++i) {
				cromosoma[i] = (Math.random() < 0.5) ? false : true;
			}
			res.setCromosoma(cromosoma);
			cromosomaFactible = res.esFactible();
		}
		res.setPoblacion(this);
		poblacion().add(res);
	}

	public double puntuacionTotal() {
		double res = 0;
		ListIterator<ag.Cromosoma> it = poblacion().listIterator();
		while (it.hasNext()) {
			final Cromosoma cromosoma = (Cromosoma) it.next();
			res += cromosoma.aptitud();
		}
		return res;
	}

}