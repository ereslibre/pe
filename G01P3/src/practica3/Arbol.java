package practica3;
import java.util.ArrayList;

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

public class Arbol {

	private Arbol      m_hi = null;
	private Arbol      m_hc = null;
	private Arbol      m_hd = null;
	private Arbol      m_padre = null;
	private Funcion    m_funcion = null;
	private Termino    m_termino = null;

	public Arbol(ArrayList<Termino> terminos, Arbol padre) {
		m_padre = padre;
		m_termino = terminos.get((int) Math.round(Math.random() * (terminos.size() - 1)));
	}

	public Arbol(ArrayList<Funcion> funciones, ArrayList<Termino> terminos, Arbol padre, int alturaMaxima, int altura) {
		m_padre = padre;
		m_funcion = funciones.get((int) Math.round(Math.random() * Funcion.TotalFunciones));
		if (altura == alturaMaxima) {
			switch (m_funcion.aridad()) {
				case 2:
					m_hd = new Arbol(Funcion.terminosControl(), this); // fall-through
				case 1:				
					m_hi = new Arbol(Funcion.terminosControl(), this);
					break;
				case 3:
					m_hi = new Arbol(Funcion.terminosControl(), this);
					m_hc = new Arbol(Funcion.terminosDatos(), this);
					m_hd = new Arbol(Funcion.terminosDatos(), this);
					break;
				default:
					break;
			}
			return;
		}
		switch (m_funcion.aridad()) {
			case 2:
				if (Math.random() < 0.5) {
					m_hd = new Arbol(Funcion.terminosControl(), this);
				} else {
					m_hd = new Arbol(funciones, terminos, this, alturaMaxima, altura + 1);
				} // fall-through
			case 1:
				if (Math.random() < 0.5) {
					m_hi = new Arbol(Funcion.terminosControl(), this);
				} else {
					m_hi = new Arbol(funciones, terminos, this, alturaMaxima, altura + 1);
				}
				break;
			case 3:
				if (Math.random() < 0.5) {
					m_hi = new Arbol(Funcion.terminosControl(), this);
				} else {
					m_hi = new Arbol(funciones, terminos, this, alturaMaxima, altura + 1);
				}
				if (Math.random() < 0.5) {
					m_hc = new Arbol(Funcion.terminosDatos(), this);
				} else {
					m_hc = new Arbol(funciones, terminos, this, alturaMaxima, altura + 1);
				}
				if (Math.random() < 0.5) {
					m_hd = new Arbol(Funcion.terminosDatos(), this);
				} else {
					m_hd = new Arbol(funciones, terminos, this, alturaMaxima, altura + 1);
				}
				break;
			default:
				break;
		}
	}
	
	public int numNodos() {
		int res = 0;
		if (m_hi != null) {
			res += m_hi.numNodos();
		}
		if (m_hc != null) {
			res += m_hc.numNodos();
		}
		if (m_hd != null) {
			res += m_hd.numNodos();
		}
		return res;
	}

	public Arbol hijoIzq() {
		return m_hi;
	}

	public void setHijoIzq(Arbol hijoIzq) {
		m_hi = hijoIzq;
	}

	public Arbol hijoCen() {
		return m_hc;
	}

	public void setHijoCen(Arbol hijoCen) {
		m_hc = hijoCen;
	}

	public Arbol hijoDer() {
		return m_hd;
	}

	public void setHijoDer(Arbol hijoDer) {
		m_hd = hijoDer;
	}

	public Arbol padre() {
		return m_padre;
	}

	public String toString() {
		if (m_funcion == null) {
			return m_termino.nombre();
		}
		String res = new String();
		switch (m_funcion.funcion()) {
			case Funcion.Not:
				res += "(NOT " + m_hi.toString() + ")";
				break;
			case Funcion.And:
				res += "(AND " + m_hi.toString() + " " + m_hd.toString() + ")";
				break;
			case Funcion.Or:
				res += "(OR " + m_hi.toString() + " " + m_hd.toString() + ")";
				break;
			case Funcion.If:
				res += "(IF " + m_hi.toString() + " " + m_hc.toString() + " " + m_hd.toString() + ")";
				break;
			default:
				break;
		}
		return res;
	}
}
