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

package practica2;

import java.util.ArrayList;

import ag.Cruce;
import ag.Problema;

public class Cromosoma extends ag.Cromosoma {

	private int[] m_cromosoma = null;

	Cromosoma() {
		super();
		m_cromosoma = new int[Problema.self().tamCromosoma()];
	}

	@Override
	public double aptitud() {
		double res = Utilidades.getDist(0, m_cromosoma[0]);
		for (int i = 0; i < m_cromosoma.length - 1; ++i) {
			res += Utilidades.getDist(m_cromosoma[i], m_cromosoma[i + 1]);
		}
		res += Utilidades.getDist(m_cromosoma[m_cromosoma.length - 1], 0);
		return 10000000 - res;
	}

	@Override
	public double evaluacion() {
		double res = Utilidades.getDist(0, m_cromosoma[0]);
		for (int i = 0; i < m_cromosoma.length - 1; ++i) {
			res += Utilidades.getDist(m_cromosoma[i], m_cromosoma[i + 1]);
		}
		res += Utilidades.getDist(m_cromosoma[m_cromosoma.length - 1], 0);
		return res;
	}

	@Override
	public Object clone() {
		Cromosoma res = new Cromosoma();
		res.m_madre = m_madre;
		res.m_padre = m_padre;
		res.m_poblacion = m_poblacion;
		for (int i = 0; i < m_cromosoma.length; ++i) {
			res.m_cromosoma[i] = m_cromosoma[i];
		}
		return res;
	}

	@Override
	public Cruce cruzar(ag.Cromosoma cromosoma) {
//		final int tamCromosoma = Problema.self().tamCromosoma();
//		int posCruce = (int) ((Math.random() * ((double) tamCromosoma - 1.0)));
//
//		Cromosoma hijo1 = Factoria.generaCromosoma(m_poblacion);
//		int hijo1c[] = new int[tamCromosoma];
//		for (int i = 0; i < tamCromosoma; ++i) {
//			if (i <= posCruce) {
//				hijo1c[i] = ((int[]) genotipo())[i];
//			} else {
//				hijo1c[i] = ((int[]) cromosoma.genotipo())[i];
//			}
//		}
//		hijo1.setCromosoma(hijo1c);
//		hijo1.setMadre(this);
//		hijo1.setPadre(cromosoma);
//
//		Cromosoma hijo2 = Factoria.generaCromosoma(m_poblacion);
//		int hijo2c[] = new int[tamCromosoma];
//		for (int i = 0; i < tamCromosoma; ++i) {
//			if (i <= posCruce) {
//				hijo2c[i] = ((int[]) cromosoma.genotipo())[i];
//			} else {
//				hijo2c[i] = ((int[]) genotipo())[i];
//			}
//		}
//		hijo2.setCromosoma(hijo2c);
//		hijo2.setMadre(this);
//		hijo2.setPadre(cromosoma);
//
//		return new Cruce(hijo1, hijo2);
		
		//////////////////////////////////////////////////////////////////////
		//////PMX
		final int tamCromosoma = Problema.self().tamCromosoma();

		int posCruce1 = 0;
		int posCruce2 = 0;
		int aux;
		while (posCruce1 == posCruce2) {
			posCruce1 = (int) ((Math.random() * ((double) tamCromosoma - 1.0)));
			posCruce2 = (int) ((Math.random() * ((double) tamCromosoma - 1.0)));
		}
		

		if (posCruce2 < posCruce1) {
			aux = posCruce2;
			posCruce2 = posCruce1;
			posCruce1 = aux;
		}
		//Creamos los cromosomas
		Cromosoma hijo1 = Factoria.generaCromosoma(m_poblacion);
		Cromosoma hijo2 = Factoria.generaCromosoma(m_poblacion);
		int hijo1c[] = new int[tamCromosoma];
		int hijo2c[] = new int[tamCromosoma];
		int hijo1cAux[] = new int[hijo1c.length];
		int hijo2cAux[] = new int[hijo2c.length];
		ArrayList<Integer> acum1 = new ArrayList<Integer>();
		ArrayList<Integer> acum2 = new ArrayList<Integer>();
		
		//Intercambio la parte central en el vector auxiliar
		for(int i = posCruce1; i < posCruce2; i++) {
			hijo1cAux[i] = hijo2c[i]; acum1.add(hijo2c[i]);
			hijo2cAux[i] = hijo1c[i]; acum2.add(hijo1c[i]);
		}
		//recorro la primera parte del vector hasta el primer cruce
		for(int i = 0; i < posCruce1;i++) {
	    	if (acum1.contains(hijo1c[i])) {//Compruebo si se ha utilizado antes 
	    		hijo1cAux[i] = hijo1c[buscar(hijo2c, hijo1c[i])];
	    		while(acum1.contains(hijo1cAux[i])) {
	    			hijo1cAux[i] = hijo1c[buscar(hijo2c, hijo1cAux[i])];
	    		}
	    		acum1.add(hijo1cAux[i]);
	    	}		
	    	else {
	    		hijo1cAux[i] = hijo1c[i];
	    		acum1.add(hijo1cAux[i]);
	    	}
	    	
	    	if (acum2.contains(hijo2c[i])) {
	    		hijo2cAux[i] = hijo2c[buscar(hijo1c, hijo2c[i])];
	    		while(acum2.contains(hijo2cAux[i])) {
	    			hijo2cAux[i] = hijo2c[buscar(hijo1c, hijo2cAux[i])];
	    		}
	    		acum2.add(hijo2cAux[i]);
	    	}		
	    	else {
	    		hijo2cAux[i] = hijo2c[i];
	    		acum2.add(hijo2cAux[i]);
	    	}
		}
		//se recorre la segunda parte del vector
		for(int i = posCruce2; i < hijo1c.length;i++) {
	    	if (acum1.contains(hijo1c[i])) {
	    		hijo1cAux[i] = hijo1c[buscar(hijo2c, hijo1c[i])];
	    		while(acum1.contains(hijo1cAux[i])) {
	    			hijo1cAux[i] = hijo1c[buscar(hijo2c, hijo1cAux[i])];
	    		}
	    		acum1.add(hijo1cAux[i]);
	    	}		
	    	else {
	    		hijo1cAux[i] = hijo1c[i];
    		    acum1.add(hijo1cAux[i]);
	    	}
	    	if (acum2.contains(hijo2c[i])) {
	    		hijo2cAux[i] = hijo2c[buscar(hijo1c, hijo2c[i])];
	    		while(acum2.contains(hijo2cAux[i])) {
	    			hijo2cAux[i] = hijo2c[buscar(hijo1c, hijo2cAux[i])];
	    		}
	    		acum2.add(hijo2cAux[i]);
	    	}		
	    	else {
	    		hijo2cAux[i] = hijo2c[i];
	    		acum2.add(hijo2cAux[i]);
	    	}
		}
		
		hijo1.setCromosoma(hijo1cAux);
		hijo1.setMadre(this);
		hijo1.setPadre(cromosoma);
		
		hijo2.setCromosoma(hijo2cAux);
		hijo2.setMadre(this);
		hijo2.setPadre(cromosoma);
		return new Cruce(hijo1, hijo2);
	}

	@Override
	public boolean esFactible() {
		return true;
//		for (int i = 0; i < m_cromosoma.length - 1; ++i) {
//			for (int j = i + 1; j < m_cromosoma.length; ++j) {
//				if (m_cromosoma[i] == m_cromosoma[j]) {
//					// Assert. Esto nunca debería ocurrir. No sabemos por qué "assert false" no hace su
//                    // trabajo.
//                    ArrayList<Integer> assertForzado = new ArrayList<Integer>();
//                    assertForzado.set(100, 1);
//					return false;
//				}
//			}
//		}
//		return true;
	}

	@Override
	public Object fenotipo() {
		ArrayList<String> res = new ArrayList<String>();
		res.add(Utilidades.getCiudad(0));
		for (int i = 0; i < m_cromosoma.length; ++i) {
			res.add(Utilidades.getCiudad(m_cromosoma[i]));
		}
		return res;
	}

	@Override
	public Object genotipo() {
		return m_cromosoma;
	}

	@Override
	public void mutar() {
//		for (int i = 0; i < Problema.self().tamCromosoma(); ++i) {
//			if (Math.random() < Problema.self().probMutacion()) {
//				m_cromosoma[i] = (int) (((Problema.self().tamCromosoma() - 1) * Math.random()) + 1);
//			}
//		}
/////Hay que hacer aqui el switch case		
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		////Insercion/////////
		final int tamCromosoma = Problema.self().tamCromosoma();
		//Se elige la posicion a insertar y la ciudad
		int posInsertar = (int) ((Math.random() * ((double) tamCromosoma - 1.0)));
	    int ciudad = (int)((Problema.self().tamCromosoma() - 1) * Math.random()) + 1;
	    //Se busca la posicion de dicha ciudad
	    int posOriginal = buscar(((int[]) genotipo()), ciudad);
	    //Se desplaza para dejar hueco para insertar la ciudad
	    if(posInsertar < posOriginal) {
	       for (int i = posOriginal;i > posInsertar; i--) {
	    	   ((int[]) genotipo())[i] = ((int[]) genotipo())[i-1];
	       }
	       ((int[]) genotipo())[posInsertar] = ciudad;
	    }
	    
	    else if (posInsertar > posOriginal){
	    	 for (int i = posOriginal;i < posInsertar; i++) {
	    		 ((int[]) genotipo())[i] = ((int[]) genotipo())[i+1];
		       }
	    	 ((int[]) genotipo())[posInsertar] = ciudad;
	    }
	    else {
	    	((int[]) genotipo())[posInsertar] = ciudad;
	    }
	    
	    ////////////////////////////////////////////////////////////////////////////////////////////////////
	    //////Inversion/////
		int posCruce1 = 0;
		int posCruce2 = 0;
		int aux;
		int aux1;
		while (posCruce1 == posCruce2) {
			posCruce1 = (int) ((Math.random() * ((double) tamCromosoma - 1.0)));
			posCruce2 = (int) ((Math.random() * ((double) tamCromosoma - 1.0)));
		}
		
		if (posCruce2 < posCruce1) {
			aux = posCruce2;
			posCruce2 = posCruce1;
			posCruce1 = aux;
		}
	     	    
		for(int i = posCruce1; i < (posCruce1+posCruce2)/2; i++) {
			   aux1 = ((int[]) genotipo())[i];
			   ((int[]) genotipo())[i] = ((int[]) genotipo())[(posCruce2-1)-(i-posCruce1)];
			   ((int[]) genotipo())[(posCruce2-1)-(i-posCruce1)] = aux1;
		}
		
	}

	public int[] cromosoma() {
		return m_cromosoma;
	}

	public void setCromosoma(int[] cromosoma) {
		m_cromosoma = cromosoma;
	}
	
	public int buscar(int[] vector, int elem) {
		int i = 0;
		boolean encontrado = false;
		while (i < vector.length && !encontrado) {
			encontrado = vector[i] == elem;
			i++;
		}
		if (encontrado) 
			return i-1;
		else return -1;
	}

}
