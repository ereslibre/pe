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

import ag.Problema;

public class Arbol {

	private Arbol   m_hi = null;
	private Arbol   m_hc = null;
	private Arbol   m_hd = null;
	private Arbol   m_padre = null;
	private Funcion m_funcion = null;
	private Termino m_termino = null;
	private int     m_alturaMaxima = 0;

	public Arbol() {
	}

	public Arbol(ArrayList<Termino> terminos, Arbol padre) {
		m_padre = padre;
		m_termino = terminos.get((int) Math.round(Math.random() * (terminos.size() - 1)));
	}

	public Arbol(ArrayList<Funcion> funciones, ArrayList<Termino> terminos, Arbol padre, int alturaMaxima, int altura) {
		m_padre = padre;
		m_funcion = funciones.get((int) Math.round(Math.random() * (Funcion.funciones().size() - 1)));
		m_alturaMaxima = alturaMaxima;
		if (altura == alturaMaxima - 1) {
			switch (m_funcion.aridad()) {
				case 2:
					m_hd = new Arbol(Funcion.terminos(), this); // fall-through
				case 1:				
					m_hi = new Arbol(Funcion.terminos(), this);
					break;
				case 3:
					m_hi = new Arbol(Funcion.terminos(), this);
					m_hc = new Arbol(Funcion.terminos(), this);
					m_hd = new Arbol(Funcion.terminos(), this);
					break;
				default:
					break;
			}
			return;
		}
		switch (m_funcion.aridad()) {
			case 2:
				m_hd = new Arbol(funciones, terminos, this, alturaMaxima, altura + 1); // fall-through
			case 1:
				m_hi = new Arbol(funciones, terminos, this, alturaMaxima, altura + 1);
				break;
			case 3:
				m_hi = new Arbol(funciones, terminos, this, alturaMaxima, altura + 1);
				m_hc = new Arbol(funciones, terminos, this, alturaMaxima, altura + 1);
				m_hd = new Arbol(funciones, terminos, this, alturaMaxima, altura + 1);
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
		return res + 1;
	}

	public int profundidad() {
		if (m_termino != null) {
			return 0;
		}
		switch (m_funcion.funcion()) {
			case Funcion.Not:
				return m_hi.profundidad() + 1;
			case Funcion.And:
			case Funcion.Or:
				return Math.max(m_hi.profundidad(), m_hd.profundidad()) + 1;
			case Funcion.If:
				return Math.max(Math.max(m_hi.profundidad(), m_hc.profundidad()), m_hd.profundidad()) + 1;
			default:
				break;
		}
		return 0; // no se alcanza nunca
	}

	public Arbol hijoIzq() {
		return m_hi;
	}

	public void setHijoIzq(Arbol hijoIzq) {
		m_hi = hijoIzq;
		m_hi.setPadre(this);
	}

	public Arbol hijoCen() {
		return m_hc;
	}

	public void setHijoCen(Arbol hijoCen) {
		m_hc = hijoCen;
		m_hc.setPadre(this);
	}

	public Arbol hijoDer() {
		return m_hd;
	}

	public void setHijoDer(Arbol hijoDer) {
		m_hd = hijoDer;
		m_hd.setPadre(this);
	}

	public Arbol padre() {
		return m_padre;
	}

	public void setPadre(Arbol padre) {
		m_padre = padre;
	}

	public int desequilibrio() {
		if (m_hi != null && m_hd != null && m_hc != null) {
			return Math.max(Math.max(m_hi.desequilibrio(), m_hc.desequilibrio()), m_hd.desequilibrio());
		} else if (m_hi != null && m_hd != null) {
			return Math.abs(m_hi.profundidad() - m_hd.profundidad());
		} else if (m_hi != null) {
			return m_hi.desequilibrio();
		}
		return 0;
	}

	public boolean evaluar(boolean a0, boolean a1, boolean d0, boolean d1, boolean d2, boolean d3) {
		if (m_funcion == null) {
			if (m_termino.nombre() == "A0") {
				return a0;
			}
			if (m_termino.nombre() == "A1") {
				return a1;
			}
			if (m_termino.nombre() == "D0") {
				return d0;
			}
			if (m_termino.nombre() == "D1") {
				return d1;
			}
			if (m_termino.nombre() == "D2") {
				return d2;
			}
			return d3;
		}
		switch (m_funcion.funcion()) {
			case Funcion.Not:
				return !m_hi.evaluar(a0, a1, d0, d1, d2, d3);
			case Funcion.And:
				return m_hi.evaluar(a0, a1, d0, d1, d2, d3) && m_hd.evaluar(a0, a1, d0, d1, d2, d3);
			case Funcion.Or:
				return m_hi.evaluar(a0, a1, d0, d1, d2, d3) || m_hd.evaluar(a0, a1, d0, d1, d2, d3);
			case Funcion.If:
				if (m_hi.evaluar(a0, a1, d0, d1, d2, d3)) {
					return m_hc.evaluar(a0, a1, d0, d1, d2, d3);
				}
				return m_hd.evaluar(a0, a1, d0, d1, d2, d3);
			default:
				break;
		}
		return false; // no se alcanza nunca
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

	public Object clone() {
		Arbol res = new Arbol();
		res.m_padre = m_padre;
		res.m_alturaMaxima = m_alturaMaxima;
		if (m_hi != null) {
			res.m_hi = (Arbol) m_hi.clone();
			res.m_hi.m_padre = res;
		}
		if (m_hc != null) {
			res.m_hc = (Arbol) m_hc.clone();
			res.m_hc.m_padre = res;
		}
		if (m_hd != null) {
			res.m_hd = (Arbol) m_hd.clone();
			res.m_hd.m_padre = res;
		}
		res.m_funcion = m_funcion;
		res.m_termino = m_termino;
		return res;
	}

	public ArrayList<Arbol> cruzar(Arbol arbol) {
		ArrayList<Arbol> res = new ArrayList<Arbol>();
		Arbol hijo1 = (Arbol) clone();
		Arbol hijo2 = (Arbol) arbol.clone();
		Arbol nodo1 = (Arbol) hijo1.nodoAleatorio().clone();
		Arbol nodo2 = (Arbol) hijo2.nodoAleatorio().clone();
		if (nodo1.padre() != null) {
			switch (numHijo(nodo1, nodo1.padre())) {
				case 0:
					nodo1.padre().setHijoIzq((Arbol) nodo2);
					break;
				case 1:
					nodo1.padre().setHijoCen((Arbol) nodo2);
					break;
				case 2:
					nodo1.padre().setHijoDer((Arbol) nodo2);
					break;
				default:
					break;
			}
		} else {
			hijo2 = nodo1;
		}
		if (nodo2.padre() != null) {
			switch (numHijo(nodo2, nodo2.padre())) {
				case 0:
					nodo2.padre().setHijoIzq((Arbol) nodo1);
					break;
				case 1:
					nodo2.padre().setHijoCen((Arbol) nodo1);
					break;
				case 2:
					nodo2.padre().setHijoDer((Arbol) nodo1);
					break;
				default:
					break;
			}
		} else {
			hijo1 = nodo2;
		}
		res.add(hijo1);
		res.add(hijo2);
		return res;
	}

	public Funcion funcion() {
		return m_funcion;
	}

	private int numHijo(Arbol arbol, Arbol padre) {
		if (padre.m_hi == arbol) {
			return 0;
		}
		if (padre.m_hc == arbol) {
			return 1;
		}
		return 2;
	}

	private Arbol nodoAleatorio() {
		Arbol res = null;
		Par<ArrayList<Arbol>, ArrayList<Arbol>> par = nodos();
		if (Math.random() < 0.9) {
			res = par.primero().get((int) Math.round(Math.random() * (par.primero().size() - 1)));
		} else {
			res = par.segundo().get((int) Math.round(Math.random() * (par.segundo().size() - 1)));
		}
		return res;
	}

	public void mutar() {
		if (m_hi != null) {
			mutarAux(m_hi);
		}
		if (m_hc != null) {
			mutarAux(m_hc);
		}
		if (m_hd != null) {
			mutarAux(m_hd);
		}
	}

	private void mutarAux(Arbol a) {
		if (Math.random() < Problema.self().probMutacion()) {
			if (a.m_termino != null) {
				a.m_termino = Funcion.terminos().get((int) Math.round((Math.random() * (Funcion.terminos().size() - 1))));
				return;
			}
			switch(a.m_funcion.funcion()) {
				case Funcion.And:
					a.m_funcion = new Funcion(Funcion.Or);
					break;
				case Funcion.Or:
					a.m_funcion = new Funcion(Funcion.And);
					break;
				case Funcion.If:
					Arbol aux = a.m_hc;
					a.m_hc = a.m_hd;
					a.m_hd = aux;
					break;
				default:
					break;
			}
		}
		if (a.m_hi != null) {
			mutarAux(a.m_hi);
		}
		if (a.m_hc != null) {
			mutarAux(a.m_hc);
		}
		if (a.m_hd != null) {
			mutarAux(a.m_hd);
		}
	}

	// funciones, terminos
	public Par<ArrayList<Arbol>, ArrayList<Arbol>> nodos() {
		Par<ArrayList<Arbol>, ArrayList<Arbol>> res = new Par<ArrayList<Arbol>, ArrayList<Arbol>>();

		ArrayList<Arbol> funciones = new ArrayList<Arbol>();
		ArrayList<Arbol> terminos = new ArrayList<Arbol>();

		nodosAux(this, funciones, terminos);

		res.setPrimero(funciones);
		res.setSegundo(terminos);

		return res;
	}

	private void nodosAux(Arbol a, ArrayList<Arbol> funciones, ArrayList<Arbol> terminos) {
		if (a.m_funcion != null) {
			funciones.add(a);
			if (a.m_hi != null) {
				nodosAux(a.m_hi, funciones, terminos);
			}
			if (a.m_hc != null) {
				nodosAux(a.m_hc, funciones, terminos);
			}
			if (a.m_hd != null) {
				nodosAux(a.m_hd, funciones, terminos);
			}
		} else {
			terminos.add(a);
		}
	}
}
