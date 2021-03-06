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

import java.util.ArrayList;
import java.util.ListIterator;

import ag.Problema;

import practica2.Cromosoma;
import practica2.Factoria;

public class Poblacion extends ag.Poblacion {

	public Poblacion() {
		super();
	}

	@Override
	public void anadeCromosomaAleatorio() {
		Cromosoma res = Factoria.generaCromosoma(this);
		ArrayList<Integer> cromosoma = new ArrayList<Integer>();
		int cromosomaFinal[] = new int[Problema.self().tamCromosoma()];
		for (int i = 0; i < Problema.self().tamCromosoma(); ++i) {
			cromosoma.add(i + 1);
		}
		for (int i = 0; i < Problema.self().tamCromosoma(); ++i) {
			cromosomaFinal[i] = cromosoma.remove((int) Math.round(((cromosoma.size() - 1.0) * Math.random())));
		}
		res.setCromosoma(cromosomaFinal);
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