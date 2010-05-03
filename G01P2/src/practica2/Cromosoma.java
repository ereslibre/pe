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
		return (2000 * m_cromosoma.length) - res;
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

					posCruce1 = Math.max(1, posCruce1);
					posCruce2 = Math.min(tamCromosoma - 2, posCruce2);

					ArrayList<Integer> elemHijo1 = new ArrayList<Integer>();
					ArrayList<Integer> elemHijo2 = new ArrayList<Integer>();
					for (int i = posCruce1; i <= posCruce2; ++i) {
						elemHijo1.add(m_cromosoma[i]);
						hijo1c[i] = m_cromosoma[i];
						elemHijo2.add(((practica2.Cromosoma) cromosoma).m_cromosoma[i]);
						hijo2c[i] = ((practica2.Cromosoma) cromosoma).m_cromosoma[i];
					}

					int posAct1 = posCruce2 + 1;
					int posAct2 = posAct1;
					int posAct = posAct1;
					for (int i = 0; i < tamCromosoma; ++i) {
						if (!elemHijo1.contains(((practica2.Cromosoma) cromosoma).m_cromosoma[posAct])) {
							hijo1c[posAct1] = ((practica2.Cromosoma) cromosoma).m_cromosoma[posAct];
							posAct1 = (posAct1 + 1) % tamCromosoma;
						}
						if (!elemHijo2.contains(m_cromosoma[posAct])) {
							hijo2c[posAct2] = m_cromosoma[posAct];
							posAct2 = (posAct2 + 1) % tamCromosoma;
						}
						posAct = (posAct + 1) % tamCromosoma;
					}
				}
				break;
			case 2: { // Variante OX
				}
				break;
			case 3: { // Ciclos
					for (int i = 0; i < tamCromosoma; ++i) {
						hijo1c[i] = -1;
						hijo2c[i] = -1;
					}

					int i = 0;
					while (true) {
						hijo1c[i] = m_cromosoma[i];
						i = buscar(m_cromosoma, ((practica2.Cromosoma) cromosoma).m_cromosoma[i]);
						if (hijo1c[i] != -1) {
							break;
						}
					}
					for (int j = 0; j < m_cromosoma.length; ++j) {
						if (hijo1c[j] == -1) {
							hijo1c[j] = ((practica2.Cromosoma) cromosoma).m_cromosoma[j];
						}
					}

					i = 0;
					while (true) {
						hijo2c[i] = ((practica2.Cromosoma) cromosoma).m_cromosoma[i];
						i = buscar(((practica2.Cromosoma) cromosoma).m_cromosoma, m_cromosoma[i]);
						if (hijo2c[i] != -1) {
							break;
						}
					}
					for (int j = 0; j < ((practica2.Cromosoma) cromosoma).m_cromosoma.length; ++j) {
						if (hijo2c[j] == -1) {
							hijo2c[j] = m_cromosoma[j];
						}
					}
				}
				break;
			case 4: { // ERX
				
					ArrayList<Integer>[] tabla1 = new ArrayList[tamCromosoma];
					ArrayList<Integer>[] tabla2 = new ArrayList[tamCromosoma];
		        
					for (int i = 0; i < tabla1.length; i++) {
						tabla1[i] = new ArrayList<Integer>();
						tabla2[i] = new ArrayList<Integer>();
					}
					//Padres
					int p1[] = new int[tamCromosoma];
					int p2[] = new int[tamCromosoma];
					
					for (int i = 0; i < tamCromosoma; ++i) {
						p1[i] = ((int[]) genotipo())[i];
						p2[i] = ((int[]) cromosoma.genotipo())[i];
					}
					//Se pone las distintas ciudades vecinas en la tabla
					for (int i = 1; i <= tamCromosoma; i++) {
						//primer padre
						int posP1 = devuelvePos(m_cromosoma,i);
						int pos1P1 = p1[(posP1 - 1 + tamCromosoma) % tamCromosoma];
						int pos2P1 = p1[(posP1 + 1 + tamCromosoma) % tamCromosoma];
						tabla1[i - 1].add(pos1P1);
						tabla1[i - 1].add(pos2P1);
						tabla2[i - 1].add(pos1P1);
						tabla2[i - 1].add(pos2P1);

						//segundo padre
						int posP2 = devuelvePos(((practica2.Cromosoma) cromosoma).m_cromosoma,i);
						int pos1P2 = p2[(posP2 - 1 + tamCromosoma) % tamCromosoma];
						int pos2P2 = p2[(posP2 + 1 + tamCromosoma) % tamCromosoma];
						if (!tabla1[i - 1].contains(pos1P2)) {
							tabla1[i - 1].add(pos1P2);
							tabla2[i - 1].add(pos1P2);
						}
						if (!tabla1[i - 1].contains(pos2P2)) {
							tabla1[i - 1].add(pos2P2);
							tabla2[i - 1].add(pos2P2);
						}
					}
		        
					//primer hijo
					int i = 0;
					while (i < tamCromosoma) {
						if (i == 0) {//Caso base
							hijo1c[0] = p1[0];
							// actualizamos la tabla quitando el elemento tratado
							for (int j = 0; j < tabla1.length; j++) {
								if (tabla1[j].contains(hijo1c[0])) {
									tabla1[j].remove(new Integer(hijo1c[0]));
								}
							}
							i++;
						}else{
							int num = hijo1c[i-1];
							int min = Integer.MAX_VALUE;
							int pos = -1;
							int posAux = 0;
							for (int j = 0; j < tabla1[num-1].size(); j++) {
								posAux = (Integer) tabla1[num-1].get(j);
		                    
								if (tabla1[posAux-1].size() < min) {
									min = tabla1[posAux-1].size();
									pos = posAux;
								}
							}
							//actualizamos la tabla eliminando el elemento utilizado
							
							//en caso de no mejorar y tener un empate
							if (pos == -1) {
								//cogemos uno al azar
								ArrayList<Integer> lista = new ArrayList<Integer>();
								for (int j = 1; j < tamCromosoma+1; j++) {
									if (buscar(hijo1c,j) == -1) {
										lista.add(j);
									}
								}
								int elemento = (int) Math.random() * lista.size();
								pos = (Integer)lista.get(elemento);
								hijo1c[i] = pos;
							}else{
								hijo1c[i] = pos;
							}
							for (int j = 0; j < tabla1.length; j++) {
								if (tabla1[j].contains(pos)) {
									tabla1[j].remove(new Integer(pos));
								}
							}
							i++;
						}
					}
		        
					//segundo hijo
					i = 0;
					while (i < tamCromosoma) { 
						if (i == 0) {//caso base
							hijo2c[0] = p2[0];
							//actualizamos la tabla eliminando el elemento utilizado 
							for (int j = 0; j < tabla2.length; j++) {
								if (tabla2[j].contains(hijo2c[0])) {
									tabla2[j].remove(new Integer(hijo2c[0]));
								}
							}
							i++;
						}else{
							int num = hijo2c[i-1];
							int min = Integer.MAX_VALUE;
							int pos = -1;
							int posAux = 0;
							for (int j = 0; j < tabla2[num-1].size(); j++) {
								posAux = (Integer) tabla2[num-1].get(j);
								//poner si son iguales que eliga uno al azar
								if (tabla2[posAux-1].size() < min) {
									min = tabla2[posAux-1].size();
									pos = posAux;
								}
							}
							//ponemos la tabla bien
							
							if (pos == -1) {
								//cogemos uno al azar
								ArrayList<Integer> lista = new ArrayList<Integer>();
								for (int j = 1; j < tamCromosoma+1; j++) {
									if (buscar(hijo2c,j) == -1) {
										lista.add(j);
									}
								}
								int elemento = (int) Math.random() * lista.size();
								pos = (Integer)lista.get(elemento);
								hijo2c[i] = pos;
							}else{
								hijo2c[i] = pos;
							}
							for (int j = 0; j < tabla2.length; j++) {
								if (tabla2[j].contains(pos)) {
									tabla2[j].remove(new Integer(pos));
								}
							}
							i++;
						}           
					}
					}				
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
		for (int i = 0; i < m_cromosoma.length - 1; ++i) {
			for (int j = i + 1; j < m_cromosoma.length; ++j) {
				if (m_cromosoma[i] == m_cromosoma[j]) {
					// Assert. Esto nunca debería ocurrir. No sabemos por qué "assert false" no hace su
                    // trabajo.
                    ArrayList<Integer> assertForzado = new ArrayList<Integer>();
                    assertForzado.set(100, 1);
					return false;
				}
			}
		}
		return true;
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
							} while (swapWith == i);
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
			case 3: { // Propio (Variante de Inversión)
					for (int i = 0; i < Problema.self().tamCromosoma() / 2; ++i) {
						if (Math.random() < Problema.self().probMutacion()) {
							final int aux = m_cromosoma[i * 2];
							m_cromosoma[i * 2] = m_cromosoma[i * 2 + 1];
							m_cromosoma[i * 2 + 1] = aux;
						}
					}
				}
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
	
	private int buscar(int[] vector, int elem) {
		int i = 0;
		while (i < vector.length) {
			if (vector[i] == elem) {
				return i;
			}
			i++;
		}
		return -1;
	}

	public int devuelvePos(int[] crom, int ciudad){
        for(int i = 0; i< Problema.self().tamCromosoma(); i++){
            	if(crom[i] == ciudad){
            		return i;
            	}
        }    	
        return -1;
        }

	}
