//---------------------------------------------------------------------------

#ifndef UArbolH
#define UArbolH

#include <math.h>
#include <stdio.h>
#include <vector.h>

#define CTE_ERROR 0.2
#define MIN_ERROR 0.1
#define MAX_CAD 256
#define LONG_MAX_NOM 100
#define ARCHIVO_DATOS "datos.txt"

//---------------------------------------------------------------------------

/*Estructura de los datos de entrenamiento*/
struct TDatos {
    char planeta[LONG_MAX_NOM];
    float A;    /* Distancia media al sol */
    float P;    /* Periodo orbital */
};

/*Estructura de las funciones*/
struct TFuncion {
   int aridad;
   String nombreFun;
};

/* Clase que implementa los árboles de expresiones */
class TArbol {

  /* Atributos privados de la clase */
  private:
    String nombre;
    TArbol* hd;
    TArbol* hi;
    TArbol* padre;
    int profundidad;
    int numNodos;
    int posSimbolo;
    bool hoja, raiz;
    bool esHi;  /*Indica si el nodo es hijo izquierdo de su padre o no*/

  public:

    /* Constructoras de la clase */
    TArbol() {profundidad = 0;};
    TArbol(TArbol* arbol, TArbol* pater);
    TArbol(vector<TFuncion> * cjtoFuns, vector<String> *  cjtoTerms,
      int hmax, int prof, TArbol* pater, bool esHizq, bool esRaiz);

    /* Métodos para acceder a las variables privadas */
    String leerNombre() { return nombre; };
    int leerPosSimbolo() { return posSimbolo; };
    TArbol* leerHd() { return hd; };
    TArbol* leerHi() { return hi; };
    TArbol* leerPadre() { return padre; };
    bool esHoja() { return hoja; };
    bool esRaiz() { return raiz; };
    bool esHijoIzquierdo() { return esHi; };
    int leerProfundidad() { return profundidad;};
    int leerNumNodos() { return numNodos;};

    /* Métodos para escribir las variables privadas */
    void escribirProfundidad(int p) { profundidad=p;};
    void escribirNumNodos(int n) { numNodos=n;};
    void escribirHd(TArbol* nodo);
    void escribirHi(TArbol* nodo);
    void escribirPadre(TArbol* pater) { padre = pater; };
    void escribirRaiz(bool esRaiz) { raiz = esRaiz; };

    /* Métodos propios de la clase */
    String mostrar();
    float evaluar(float a);
    int altura();
    void actualizar(int prof);
    TArbol* nodoAleatorio();
    TArbol* buscarNodo(int n);
    void mutaNodo( vector<TFuncion>  cjtoFuns, vector<String>  cjtoTerms);

};


#endif
