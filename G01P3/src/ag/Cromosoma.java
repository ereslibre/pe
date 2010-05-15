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

public abstract class Cromosoma implements Comparable<Cromosoma> {

	protected Cromosoma m_madre;
	protected Cromosoma m_padre;
	protected Poblacion m_poblacion;

	public Cromosoma() {
		m_madre = null;
		m_padre = null;
		m_poblacion = null;
	}

	/**
	 * @return El valor de fitness o adaptación de este individuo.
	 */
	public abstract double aptitud();

	public abstract Object clone();

	/**
	 * Cruza el cromosoma actual con el cromosoma dado.
	 * 
	 * @param cromosoma El cromosoma con el que cruzaremos el cromosoma actual.
	 */
	public abstract Cruce cruzar(Cromosoma cromosoma);

	/**
	 * @return Si este cromosoma es un cromosoma válido o no.
	 */
	public abstract boolean esFactible();

	/**
	 * @return El valor de evaluación de este individuo.
	 */
	public abstract double evaluacion();

	/**
	 * @return El fenotipo para este cromosoma.
	 */
	public abstract Object fenotipo();

	/**
	 * @return El genotipo para este cromosoma.
	 */
	public abstract Object genotipo();

	/**
	 * @return El cromosoma madre.
	 */
	public Cromosoma madre() {
		return m_madre;
	}

	/**
	 * Muta el cromosoma actual.
	 */
	public abstract void mutar();

	/**
	 * @return El cromosoma padre.
	 */
	public Cromosoma padre() {
		return m_padre;
	}

	/**
	 * @return La población a la que pertenece este individuo.
	 */
	public Poblacion poblacion() {
		return m_poblacion;
	}

	/**
	 * @return La puntuación de este cromosoma.
	 */
	public double puntuacion() {
		return aptitud() / ((agsimple.Poblacion) poblacion()).puntuacionTotal();
	}

	/**
	 * Establece el cromosoma madre.
	 * 
	 * @param madre El cromosoma madre a establecer.
	 */
	public void setMadre(Cromosoma madre) {
		m_madre = madre;
	}

	/**
	 * Establece el cromosoma padre.
	 * 
	 * @param padre El cromosoma padre a establecer.
	 */
	public void setPadre(Cromosoma padre) {
		m_padre = padre;
	}

	/**
	 * Establece la población a la que pertenece este individuo.
	 * 
	 * @param poblacion Población a la que pertenece este individuo.
	 */
	public void setPoblacion(Poblacion poblacion) {
		m_poblacion = poblacion;
	}

	public int compareTo(Cromosoma c) {
		if (aptitud() < c.aptitud()) {
			return -1;
		}
		if (aptitud() > c.aptitud()) {
			return 1;
		}
		return 0;
	}

	public String toString() {
		return String.valueOf(aptitud());
	}

}
