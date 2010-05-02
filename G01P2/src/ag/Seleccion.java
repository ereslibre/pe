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
	
	static public void torneo(Poblacion poblacion, Poblacion res) {
		for (int i = 0; i < Problema.self().tamPoblacion(); ++i) {
			int r1 = 0;
			int r2 = 0;
			int r3 = 0;
			while (r1 == r2 || r2 == r3 || r1 == r3) {
					r1 = (int) (Math.random() * (Problema.self().tamPoblacion() - 1));
					r2 = (int) (Math.random() * (Problema.self().tamPoblacion() - 1));
					r3 = (int) (Math.random() * (Problema.self().tamPoblacion() - 1));
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
	
	static public void ranking(Poblacion poblacion, Poblacion res) {
		// Primero se ordena por orden decreciente de puntuacion
		int[] ordenados = ordenaDecreciente(poblacion);
		float[] puntAcum = new float[Problema.self().tamPoblacion()];
		float puntTotal = 0;
		ArrayList<Cromosoma> poblacionAux = new ArrayList<Cromosoma>();
		for(int i=0;i<Problema.self().tamPoblacion();i++){
			puntAcum[i]=(float) ((1.5-2*(1.5-1)*((i-1)/(Problema.self().tamPoblacion()-1)))/Problema.self().tamPoblacion());
			puntTotal =+ puntAcum[i];
			poblacionAux.add((Cromosoma) poblacion.poblacion().get(ordenados[i]));
		}
		puntAcum[0]=puntAcum[0]/puntTotal;
		for(int i=1;i<Problema.self().tamPoblacion();i++){
			puntAcum[i]=puntAcum[i-1] + (puntAcum[i]/puntTotal);
		}
		
		for(int i=0;i<Problema.self().tamPoblacion();i++){
			float prob = (float) Math.random();
			int seleccionado = buscar(puntAcum, prob);
			res.anadeCromosoma((Cromosoma) poblacion.poblacion().get(seleccionado).clone());
		}
	}
	
	private static int buscar(float[] vector, float valor) {
	   	int pos = 0;
	   	while(pos<vector.length && vector[pos]<valor){
	   		pos++;
	   	}
	   	if(pos>=vector.length){
	   		pos--;
	   	}
	   	return pos;
   }
	
	private static int[] ordenaDecreciente(Poblacion poblacion) {
		int[] ordenado = new int[Problema.self().tamPoblacion()];
		boolean[] usados = new boolean[Problema.self().tamPoblacion()];
		for(int i=0;i<usados.length;i++){
			usados[i]=false;
		}
		for(int i=0;i<Problema.self().tamPoblacion();i++){
			double mejorPunt = poblacion.poblacion().get(0).aptitud();
			int posMejor = 0;
			for(int j=1;j<Problema.self().tamPoblacion();j++){
				if(poblacion.poblacion().get(j).aptitud()>mejorPunt && !usados[j]){
					posMejor=j;
					mejorPunt = poblacion.poblacion().get(j).aptitud();
				}
			}
			ordenado[i]=posMejor;
			usados[posMejor]=true;
		}
		return ordenado;
	}

}
