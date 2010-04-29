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
		final int tamCromosoma = Problema.self().tamCromosoma();
		Cromosoma hijo1 = Factoria.generaCromosoma(m_poblacion);
		Cromosoma hijo2 = Factoria.generaCromosoma(m_poblacion);
		int hijo1c[] = new int[tamCromosoma];
		int hijo2c[] = new int[tamCromosoma];

		switch (Problema.self().ventanaPrincipal().cruceSeleccionado()) {
			case 0: { // PMX	
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
					
					for (int i = 0; i < tamCromosoma;++i){
						 hijo1c[i] = ((int[]) genotipo())[i];
						 hijo2c[i] = ((int[]) cromosoma.genotipo())[i];
					}

					int hijo1cAux[] = new int[hijo1c.length];
					int hijo2cAux[] = new int[hijo2c.length];
					ArrayList<Integer> acum1 = new ArrayList<Integer>();
					ArrayList<Integer> acum2 = new ArrayList<Integer>();
					
					for (int i = posCruce1; i < posCruce2; i++) {
						hijo1cAux[i] = hijo2c[i]; acum1.add(hijo2c[i]);
						hijo2cAux[i] = hijo1c[i]; acum2.add(hijo1c[i]);
					}

					for (int i = 0; i < posCruce1;i++) {
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

					for (int i = posCruce2; i < hijo1c.length;i++) {
				    	if (acum1.contains(hijo1c[i])) {
				    		hijo1cAux[i] = hijo1c[buscar(hijo2c, hijo1c[i])];
				    		while(acum1.contains(hijo1cAux[i])) {
				    			hijo1cAux[i] = hijo1c[buscar(hijo2c, hijo1cAux[i])];
				    		}
				    		acum1.add(hijo1cAux[i]);
				    	} else {
				    		hijo1cAux[i] = hijo1c[i];
			    		    acum1.add(hijo1cAux[i]);
				    	}
				    	if (acum2.contains(hijo2c[i])) {
				    		hijo2cAux[i] = hijo2c[buscar(hijo1c, hijo2c[i])];
				    		while(acum2.contains(hijo2cAux[i])) {
				    			hijo2cAux[i] = hijo2c[buscar(hijo1c, hijo2cAux[i])];
				    		}
				    		acum2.add(hijo2cAux[i]);
				    	} else {
				    		hijo2cAux[i] = hijo2c[i];
				    		acum2.add(hijo2cAux[i]);
				    	}
					}
					hijo1c = hijo1cAux;
					hijo2c = hijo2cAux;
				}
				break;
			case 1: { // OX
					int posCruce1 = 0;
					int posCruce2 = 0;

					while (posCruce1 == posCruce2) {
						posCruce1 = (int) ((Math.random() * ((double) tamCromosoma - 1.0)));
						posCruce2 = (int) ((Math.random() * ((double) tamCromosoma - 1.0)));
					}
	
					if (posCruce2 < posCruce1) {
						final int aux = posCruce2;
						posCruce2 = posCruce1;
						posCruce1 = aux;
					}
					
					for (int i = 0; i < tamCromosoma;++i){
						 hijo1c[i] = ((int[]) genotipo())[i];
						 hijo2c[i] = ((int[]) cromosoma.genotipo())[i];
					}
				}
				break;
			case 2: // Variante OX
				break;
			case 3: // Ciclos
				break;
			case 4: // ERX
				break;
			case 5: // Codificación Ordinal
				break;
			case 6: // Propio
				break;
			default:
				break;
		}

		hijo1.setCromosoma(hijo1c);
		hijo1.setMadre(this);
		hijo1.setPadre(cromosoma);
		
		hijo2.setCromosoma(hijo2c);
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
		switch (Problema.self().ventanaPrincipal().mutacionSeleccionada()) {
			case 0: { // Inserción
					int posAleat1 = 0;
					int posAleat2 = 0;
	
					while (posAleat1 == posAleat2) {
						posAleat1 = (int) (Math.random() * (Problema.self().tamCromosoma() - 1));
						posAleat2 = (int) (Math.random() * (Problema.self().tamCromosoma() - 1)); 
					}

					if (posAleat2 < posAleat1) {
						final int aux = posAleat1;
						posAleat1 = posAleat2;
						posAleat2 = aux;
					}
	
					final int c = m_cromosoma[posAleat2];
					for (int i = posAleat2; i > posAleat1; --i) {
						m_cromosoma[i] = m_cromosoma[i - 1];
					}
					m_cromosoma[posAleat1] = c;
				}
				break;
			case 1: { // Intercambio
					for (int i = 0; i < Problema.self().tamCromosoma(); ++i) {
						if (Math.random() <= Problema.self().probMutacion()) {
							int swapWith = 0;
							do {
								swapWith = (int) ((Math.random() * ((double) Problema.self().tamCromosoma() - 1.0)));
							} while (swapWith != i);
							final int aux = m_cromosoma[i];
							m_cromosoma[i] = m_cromosoma[swapWith];
							m_cromosoma[swapWith] = aux;
						}
					}
				}
				break;
			case 2: { // Inversión
					int posCruce1 = 0;
					int posCruce2 = 0;
					while (posCruce1 == posCruce2) {
						posCruce1 = (int) ((Math.random() * ((double) Problema.self().tamCromosoma() - 1.0)));
						posCruce2 = (int) ((Math.random() * ((double) Problema.self().tamCromosoma() - 1.0)));
					}
					
					if (posCruce2 < posCruce1) {
						final int aux = posCruce2;
						posCruce2 = posCruce1;
						posCruce1 = aux;
					}
				     	    
					for (int i = posCruce1; i <= (posCruce1 + posCruce2) / 2; ++i) {
						   final int aux1 = m_cromosoma[i];
						   m_cromosoma[i] = m_cromosoma[posCruce2 - (i - posCruce1)];
						   m_cromosoma[posCruce2 - (i - posCruce1)] = aux1;
					}
				}
				break;
			case 3: // Propio
				break;
			default:
				break;
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
		while (i < vector.length) {
			if (vector[i] == elem) {
				return i;
			}
			i++;
		}
		return -1;
	}

}
