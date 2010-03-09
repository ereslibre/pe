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

public abstract class Poblacion {

	private ArrayList<Cromosoma> m_poblacion;
	private Problema             m_problema;

	public Poblacion(Problema problema) {
		m_poblacion = new ArrayList<Cromosoma>();
		m_problema = problema;
	}

	/**
	 * Añade a la población un cromosoma generado aleatoriamente.
	 */
	protected abstract void anadeCromosomaAleatorio();

	/**
     * @return Genera un cromosoma vacío.
     */
	public abstract Cromosoma genCromosomaVacio();

	/**
     * Genera una población inicial.
     */
	public void genPoblacionInicial() {
		for (int i = 0; i < problema().tamPoblacion(); ++i) {
			anadeCromosomaAleatorio();
		}
	}

	/**
     * Añade un cromosoma a la población.
     */
	public void anadeCromosoma(Cromosoma cromosoma) {
		m_poblacion.add(cromosoma);
	}

	/**
	 * @return La población actual.
	 */
	public ArrayList<Cromosoma> poblacion() {
		return m_poblacion;
	}

	public Problema problema() {
		return m_problema;
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
