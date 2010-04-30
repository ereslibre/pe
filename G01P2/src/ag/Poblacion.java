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
import java.util.ListIterator;

public abstract class Poblacion {

	private ArrayList<Cromosoma> m_poblacion;
	private ArrayList<Double>    m_puntuacionesAcumuladas;
	private Cromosoma            m_mejor;
	private Double               m_aptitudMedia;

	public Poblacion() {
		m_poblacion = new ArrayList<Cromosoma>();
		m_mejor = null;
	}

	/**
     * Añade un cromosoma a la población.
     */
	public void anadeCromosoma(Cromosoma cromosoma) {
		m_poblacion.add(cromosoma);
		cromosoma.setPoblacion(this);
	}

	/**
	 * Añade a la población un cromosoma generado aleatoriamente.
	 */
	protected abstract void anadeCromosomaAleatorio();

	public void cruzar() {
		ArrayList<Cromosoma> cruce = new ArrayList<Cromosoma>();
		int intentos = 0;
		do {
			cruce.clear();
			ListIterator<Cromosoma> it = m_poblacion.listIterator();
			while (it.hasNext()) {
				Cromosoma c = it.next();
				if (Math.random() < Problema.self().probCruce()) {
					cruce.add(c);
				}
			}
			if (cruce.size() % 2 != 0) {
				cruce.remove(0);
			}
			++intentos;
		} while (cruce.size() == 0 && intentos < 100);
		ListIterator<Cromosoma> it = cruce.listIterator();
		while (it.hasNext()) {
			Cromosoma c1 = it.next();
			Cromosoma c2 = it.next();
			Cruce resultado = null;
			while (true) {
				resultado = c1.cruzar(c2);
				if (resultado.a().esFactible() && resultado.b().esFactible()) {
					m_poblacion.set(m_poblacion.indexOf(c1), resultado.a());
					m_poblacion.set(m_poblacion.indexOf(c2), resultado.b());
					break;
				}
			}
		}
	}

	public void mutar() {
		ListIterator<Cromosoma> it = m_poblacion.listIterator();
		while (it.hasNext()) {
			it.next().mutar();
		}
	}

	public Double aptitudMedia() {
		return m_aptitudMedia;
	}

	public void evaluarPoblacion() {
		{
			m_aptitudMedia = 0.0;
			ListIterator<Cromosoma> it = m_poblacion.listIterator();
			while (it.hasNext()) {
				m_aptitudMedia += it.next().aptitud();
			}
			m_aptitudMedia /= Problema.self().tamPoblacion();
		}
		{
			m_puntuacionesAcumuladas = new ArrayList<Double>();
			Double punt = 0.0;
			ArrayList<Cromosoma> elite = Problema.self().elite();
			ListIterator<Cromosoma> it = m_poblacion.listIterator();
			while (it.hasNext()) {
				final Cromosoma c = it.next();
				if (elite.size() < Problema.self().tamPoblacion() * Problema.self().ventanaPrincipal().elitismo()) {
					elite.add((Cromosoma) c.clone());
					Collections.sort(elite);
				} else {
					ListIterator<Cromosoma> it2 = elite.listIterator();
					while (it2.hasNext()) {
						final Cromosoma e = it2.next();
						if (c.aptitud() > e.aptitud()) {
							//it2.set((Cromosoma) c.clone());
							Collections.sort(elite);
						}
					}
				}
				Problema.self().setElite(elite);
				if (m_mejor == null || m_mejor.aptitud() < c.aptitud()) {
					m_mejor = (Cromosoma) c.clone();
				}
				if (Problema.self().getMejor() == null || Problema.self().getMejor().aptitud() < c.aptitud()) {
					Problema.self().setMejor((Cromosoma) c.clone());
				}
				punt += c.puntuacion();
				m_puntuacionesAcumuladas.add(punt);
			}
		}
	}

	/**
     * Genera una población inicial.
     */
	public void genPoblacionInicial() {
		for (int i = 0; i < Problema.self().tamPoblacion(); ++i) {
			anadeCromosomaAleatorio();
		}
	}

	public Cromosoma getMejor() {
		return m_mejor;
	}

	public ArrayList<Double> getPuntuacionesAcumuladas() {
		return m_puntuacionesAcumuladas;
	}

	/**
	 * @return La población actual.
	 */
	public ArrayList<Cromosoma> poblacion() {
		return m_poblacion;
	}

	/**
	 * Establece la población actual.
	 * 
	 * @param poblacion La población a establecer.
	 */
	public void setPoblacion(ArrayList<Cromosoma> poblacion) {
		m_poblacion = poblacion;
	}

}
