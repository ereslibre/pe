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

package practica3;

import java.util.ArrayList;

import practica3.Factoria;

import ag.Cruce;
import ag.Problema;

public class Cromosoma extends ag.Cromosoma {

	private Arbol m_arbol = null;

	Cromosoma() {
		super();
	}

	@Override
	public double aptitud() {
		if (m_arbol.funcion() != null && m_arbol.funcion().funcion() == Funcion.If) {
			return evaluacion() + 1;
		}
		return evaluacion();
	}

	@Override
	public double evaluacion() {
		double res = 0;
		boolean[] vec = new boolean[6];
		for (int i = 0; i < 6; ++i) {
			vec[i] = false;
		}
		for (int i = 0; i < 64; ++i) {
			for (int j = 0; j < 6; ++j) {
				if (!vec[j]) {
					vec[j] = true;
					break;
				}
				vec[j] = false; 
			}
			boolean nuestraRes = m_arbol.evaluar(vec[0], vec[1], vec[2], vec[3], vec[4], vec[5]);
			if (MUX.test(vec[0], vec[1], vec[2], vec[3], vec[4], vec[5], nuestraRes)) {
				res += 1;
			}
		}
		return res;
	}

	@Override
	public Object clone() {
		Cromosoma c = new Cromosoma();
		c.m_madre = m_madre;
		c.m_padre = m_padre;
		c.m_poblacion = m_poblacion;
		c.m_arbol = (Arbol) m_arbol.clone();
		return c;
	}

	@Override
	public Cruce cruzar(ag.Cromosoma cromosoma) {
		ArrayList<Arbol> res = null;
		Cromosoma h1, h2 = null;
		do {
			res = m_arbol.cruzar(((practica3.Cromosoma) cromosoma).m_arbol);
		} while(res.get(0).profundidad() > Problema.self().profundidadMaxima() ||
				res.get(1).profundidad() > Problema.self().profundidadMaxima());
		h1 = Factoria.generaCromosoma(m_poblacion);
		h1.setPadre(this);
		h1.setMadre(cromosoma);
		h1.setCromosoma(res.get(0));
		h2 = Factoria.generaCromosoma(m_poblacion);
		h2.setPadre(this);
		h2.setMadre(cromosoma);
		h2.setCromosoma(res.get(1));
		return new Cruce(h1, h2);
	}

	@Override
	public boolean esFactible() {
		return true;
	}

	@Override
	public Object fenotipo() {
		return m_arbol.toString();
	}

	@Override
	public Object genotipo() {
		return m_arbol;
	}

	@Override
	public void mutar() {
		m_arbol.mutar();
	}

	public Arbol cromosoma() {
		return m_arbol;
	}

	public void setCromosoma(Arbol arbol) {
		m_arbol = arbol;
	}

}
