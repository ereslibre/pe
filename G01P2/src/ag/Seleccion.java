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
import java.util.ListIterator;

public class Seleccion {

	static public void ruleta(Poblacion poblacion, Poblacion res) {
		ArrayList<Double> puntAcum = poblacion.getPuntuacionesAcumuladas();
		for (int i = 0; i < Problema.self().tamPoblacion(); ++i) {
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
	
	static public void torneo(Poblacion poblacion, Poblacion res){

		for(int i = 0; i< Problema.self().tamPoblacion(); ++i){
			
			final int r1 = (int)(Math.random()*Problema.self().tamPoblacion());
			final int r2 = (int)(Math.random()*Problema.self().tamPoblacion());
			final int r3 = (int)(Math.random()*Problema.self().tamPoblacion());
			
			if (poblacion.poblacion().get(r1).evaluacion() > poblacion.poblacion().get(r2).evaluacion() &&
			    poblacion.poblacion().get(r1).evaluacion() > poblacion.poblacion().get(r3).evaluacion()){
					res.anadeCromosoma((Cromosoma) poblacion.poblacion().get(r1).clone());
			}else{
				if(poblacion.poblacion().get(r2).evaluacion() > poblacion.poblacion().get(r1).evaluacion() &&
				   poblacion.poblacion().get(r2).evaluacion() > poblacion.poblacion().get(r3).evaluacion()){
						res.anadeCromosoma((Cromosoma) poblacion.poblacion().get(r2).clone());
				}else{
						res.anadeCromosoma((Cromosoma) poblacion.poblacion().get(r3).clone());	
				}	
			}	
		}

	}

}
