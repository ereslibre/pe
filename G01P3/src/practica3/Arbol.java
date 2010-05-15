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

///*Estructura de los datos de entrenamiento*/
//struct TDatos {
//    char planeta[LONG_MAX_NOM];
//    float A;    /* Distancia media al sol */
//    float P;    /* Periodo orbital */
//};
//
///*Estructura de las funciones*/
//struct TFuncion {
//   int aridad;
//   String nombreFun;
//};
//
///* Clase que implementa los árboles de expresiones */
//class TArbol {
//
//  /* Atributos privados de la clase */
//  private:
//    String nombre;
//    TArbol* hd;
//    TArbol* hi;
//    TArbol* padre;
//    int profundidad;
//    int numNodos;
//    int posSimbolo;
//    bool hoja, raiz;
//    bool esHi;  /*Indica si el nodo es hijo izquierdo de su padre o no*/
//
//  public:
//
//    /* Constructoras de la clase */
//    TArbol() {profundidad = 0;};
//    TArbol(TArbol* arbol, TArbol* pater);
//    TArbol(vector<TFuncion> * cjtoFuns, vector<String> *  cjtoTerms,
//      int hmax, int prof, TArbol* pater, bool esHizq, bool esRaiz);
//
//    /* Métodos para acceder a las variables privadas */
//    String leerNombre() { return nombre; };
//    int leerPosSimbolo() { return posSimbolo; };
//    TArbol* leerHd() { return hd; };
//    TArbol* leerHi() { return hi; };
//    TArbol* leerPadre() { return padre; };
//    bool esHoja() { return hoja; };
//    bool esRaiz() { return raiz; };
//    bool esHijoIzquierdo() { return esHi; };
//    int leerProfundidad() { return profundidad;};
//    int leerNumNodos() { return numNodos;};
//
//    /* Métodos para escribir las variables privadas */
//    void escribirProfundidad(int p) { profundidad=p;};
//    void escribirNumNodos(int n) { numNodos=n;};
//    void escribirHd(TArbol* nodo);
//    void escribirHi(TArbol* nodo);
//    void escribirPadre(TArbol* pater) { padre = pater; };
//    void escribirRaiz(bool esRaiz) { raiz = esRaiz; };
//
//    /* Métodos propios de la clase */
//    String mostrar();
//    float evaluar(float a);
//    int altura();
//    void actualizar(int prof);
//    TArbol* nodoAleatorio();
//    TArbol* buscarNodo(int n);
//    void mutaNodo( vector<TFuncion>  cjtoFuns, vector<String>  cjtoTerms);
//
//};
//
//
//#endif

public class Arbol {
	private Arbol      m_hi = null;
	private Arbol      m_hc = null;
	private Arbol      m_hd = null;
	private Arbol      m_padre = null;
	private int        m_profundidad = 0;
	private int        m_funcion = -1;
	private int        m_termino = -1;

	public Arbol(ArrayList<Termino> terminos, Arbol padre) {
		m_padre = padre;
		m_termino = (int) Math.round(Math.random() * (terminos.size() - 1));
	}

	public Arbol(ArrayList<Funcion> funciones, ArrayList<Termino> terminos, Arbol padre, int alturaMaxima, int altura) {
		m_padre = padre;
		m_funcion = (int) Math.round(Math.random() * Funcion.TotalFunciones);
		if (altura == alturaMaxima) {
			switch (funciones.get(m_funcion).aridad()) {
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
		switch (funciones.get(m_funcion).aridad()) {
			case 2:
				if (Math.random() < 0.5) {
					m_hd = new Arbol(Funcion.terminosControl(), this);
				} else {
					m_hd = new Arbol(funciones, terminos, this, alturaMaxima, altura + 1);
				}
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
}
