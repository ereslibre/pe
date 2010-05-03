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

package ag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ListIterator;

public class Seleccion {

	static public void ruleta(Poblacion poblacion, Poblacion res) {
		ArrayList<Double> puntAcum = poblacion.getPuntuacionesAcumuladas();
		for (int i = 0; i < Problema.self().tamPoblacion() * (1 - Problema.self().tamElite()); ++i) {
			final double r = Math.random();
			ListIterator<Double> it2 = puntAcum.listIterator();
			int k = 0;
			while (it2.hasNext()) {
				final Double j = it2.next();
				if (r <= j) {
					res.anadeCromosoma((Cromosoma) poblacion.poblacion().get(k).clone());
					break;
				}
				++k;
			}
		}
	}
	
	static public void torneo(Poblacion poblacion, Poblacion res) {
		for (int i = 0; i < Problema.self().tamPoblacion() * (1 - Problema.self().tamElite()); ++i) {
			int r1 = 0;
			int r2 = 0;
			int r3 = 0;
			while (r1 == r2 || r2 == r3 || r1 == r3) {
					r1 = (int) (Math.random() * (Problema.self().tamPoblacion() * (1 - Problema.self().tamElite()) - 1));
					r2 = (int) (Math.random() * (Problema.self().tamPoblacion() * (1 - Problema.self().tamElite()) - 1));
					r3 = (int) (Math.random() * (Problema.self().tamPoblacion() * (1 - Problema.self().tamElite()) - 1));
			}
			if (poblacion.poblacion().get(r1).aptitud() > poblacion.poblacion().get(r2).aptitud() &&
			    poblacion.poblacion().get(r1).aptitud() > poblacion.poblacion().get(r3).aptitud()) {
					res.anadeCromosoma((Cromosoma) poblacion.poblacion().get(r1).clone());
			} else {
				if (poblacion.poblacion().get(r2).aptitud() > poblacion.poblacion().get(r1).aptitud() &&
				    poblacion.poblacion().get(r2).aptitud() > poblacion.poblacion().get(r3).aptitud()) {
					res.anadeCromosoma((Cromosoma) poblacion.poblacion().get(r2).clone());
				} else {
					res.anadeCromosoma((Cromosoma) poblacion.poblacion().get(r3).clone());	
				}
			}	
		}
	}

	static private class CriterioOrden implements Comparator<Cromosoma> {
		@Override
		public int compare(Cromosoma o1, Cromosoma o2) {
			if (o1.aptitud() > o2.aptitud()) {
				return 1;
			} else if (o1.aptitud() < o2.aptitud()) {
				return -1;
			} else {
				return 0;
			}
		}
	}

	static public void ranking(Poblacion poblacion, Poblacion res) {
		ArrayList<Cromosoma> p = poblacion.poblacion();
		Collections.sort(p, new CriterioOrden());

		float[] puntAcum = new float[(int) (Problema.self().tamPoblacion() * (1 - Problema.self().tamElite()))];
		float puntTotal = 0;
		ArrayList<Cromosoma> poblacionAux = new ArrayList<Cromosoma>();
		for (int i = 0; i < Problema.self().tamPoblacion() * (1 - Problema.self().tamElite()); ++i){
			puntAcum[i] = (float) ((1.5 - 2.0 * (1.5 - 1.0) * ((i - 1.0) / (Problema.self().tamPoblacion() - 1))) / Problema.self().tamPoblacion());
			puntTotal += puntAcum[i];
			poblacionAux.add((Cromosoma) (p.get(i)));
		}

		puntAcum[0] = puntAcum[0] / puntTotal;
		for (int i = 1; i < Problema.self().tamPoblacion() * (1 - Problema.self().tamElite()); ++i){
			puntAcum[i] = puntAcum[i - 1] + (puntAcum[i] / puntTotal);
		}

		for (int i = 0; i < Problema.self().tamPoblacion() * (1 - Problema.self().tamElite()); ++i){
			float prob = (float) Math.random();
			int seleccionado = buscar(puntAcum, prob);
			res.anadeCromosoma((Cromosoma) poblacion.poblacion().get(seleccionado).clone());
		}
	}
	
	private static int buscar(float[] vector, float valor) {
	   	int pos = 0;
	   	while(pos < vector.length && vector[pos] < valor){
	   		pos++;
	   	}
	   	if(pos >= vector.length){
	   		pos--;
	   	}
	   	return pos;
	}

}
