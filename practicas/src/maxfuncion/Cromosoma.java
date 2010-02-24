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
 * para valores de x comprendidos entre [-10, 10].
 */
public class Cromosoma extends agsimple.Cromosoma {

	public Cromosoma(int tamCromosoma) {
		super(tamCromosoma);
	}

	@Override
	public double aptitud() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Cruce cruzar(ag.Cromosoma cromosoma) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean esFactible() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double evaluacion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object fenotipo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object genotipo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void mutar() {
		// TODO Auto-generated method stub
		
	}

}
