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

import ag.Cruce;

/**
 * Cromosoma para el problema de maximizar el valor de y de la función:
 * 
 * 		y = 3 * x + 5
 * 
 * para valores de x comprendidos entre [0, 10].
 */
public class Cromosoma extends agsimple.Cromosoma {

	public Cromosoma(int tamCromosoma) {
		super(tamCromosoma);
	}

	@Override
	public double aptitud() {
		return 3 * ((Integer) fenotipo()) + 5;
	}

	@Override
	public Cruce cruzar(ag.Cromosoma cromosoma) {
		int posCruce = (int) Math.random() * (tamCromosoma() - 1);

		Cromosoma hijo1 = (Cromosoma) poblacion().genCromosomaVacio();
		boolean hijo1c[] = new boolean[tamCromosoma()];
		for (int i = 0; i < tamCromosoma(); ++i) {
			if (i <= posCruce) {
				hijo1c[i] = ((boolean[]) genotipo())[i];
			} else {
				hijo1c[i] = ((boolean[]) cromosoma.genotipo())[i];
			}
		}
		hijo1.setCromosoma(hijo1c);
		hijo1.setMadre(this);
		hijo1.setPadre(cromosoma);

		Cromosoma hijo2 = (Cromosoma) poblacion().genCromosomaVacio();
		boolean hijo2c[] = new boolean[tamCromosoma()];
		for (int i = 0; i < tamCromosoma(); ++i) {
			if (i <= posCruce) {
				hijo2c[i] = ((boolean[]) cromosoma.genotipo())[i];
			} else {
				hijo2c[i] = ((boolean[]) genotipo())[i];
			}
		}
		hijo2.setCromosoma(hijo1c);
		hijo2.setMadre(this);
		hijo2.setPadre(cromosoma);

		return new Cruce(hijo1, hijo2);
	}

	@Override
	public boolean esFactible() {
		return ((Integer) fenotipo()) <= 30;
	}

	@Override
	public double evaluacion() {
		return aptitud();
	}

	@Override
	public Object fenotipo() {
		int res = 0;
		boolean genotipo[] = (boolean[]) genotipo();
		for (int i = 0; i < tamCromosoma(); ++i) {
			res += genotipo[i] ? Math.pow(2, i) : 0;
		}
		return res;
	}

	@Override
	public Object genotipo() {
		return cromosoma();
	}

	@Override
	public void mutar() {
		boolean genotipo[] = (boolean[]) genotipo();
		for (int i = 0; i < tamCromosoma(); ++i) {
			if (Math.random() < poblacion().problema().probMutacion()) {
				genotipo[i] = genotipo[i] ? false : true;
			}
		}
	}

}
