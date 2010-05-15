//---------------------------------------------------------------------------

#include <vcl.h>
#include <math.h>

#pragma hdrstop

#include "UIndividuo.h"
#include <iostream>

//---------------------------------------------------------------------------

#pragma package(smart_init)

/*Constructora de copia*/
TIndividuo::TIndividuo(TIndividuo* indiv)
{
    aptitud=indiv->leerAptitud();
    puntuacion= indiv->leerPuntuacion();
    punt_acu= indiv->leerPuntAcu();
    arbol = new TArbol(indiv->arbol,NULL);
    hmax = indiv->hmax;
}


/* Constructora que crea los individuos aleatoriamente */
TIndividuo::TIndividuo(vector<TDatos> * datos, vector<TFuncion> * cjtoFuns,
  vector<String> * cjtoTerms, int hmax, int num_max_gen)
{
      /*Guardamos la altura máxima permitida*/
      this->hmax = hmax;

      /*Hemos de crear un árbol de altura máxima hmax*/
      arbol = new TArbol(cjtoFuns, cjtoTerms, hmax, 0, NULL, true, true);

      /*Ahora hay que rellenar el campo aptitud. Para ello llamamos
      a la función adaptacion. Cuando creamos los individuos,
      vamos por la iteración 0*/
      adaptacion( datos, 0, num_max_gen);
}

/* Calcula la aptitud de un individuo y la actualiza */
void TIndividuo::adaptacion( vector<TDatos> * datos, int iter, int nmax)
{
    double error;

    /* Calculamos la función de adaptación */
    aptitud = 0;
    /*El error depende de la iteración por la que nos vamos*/
    error = (nmax-iter-1);
    error = (error/nmax);
    error = ( CTE_ERROR * error) + MIN_ERROR;
    for ( int i=0; (unsigned) i < datos->size(); i++ )
    {
      if (entraEnRango(datos->at(i), error))
          aptitud++;
    }
}

/*Función que comprueba si el valor del individuo entra dentro
del rango de valores aceptados como válidos*/
bool TIndividuo::entraEnRango(TDatos dato, float error)
{
   float x;

   x = arbol->evaluar(dato.A);
   return ((x>(dato.P - error)) && (x<(dato.P + error)));
}

/*Procedimiento que convierte un individuo a un String*/
String TIndividuo::mostrar()
{
  return arbol->mostrar();
}

/*Método que escribe nuevo como el árbol de este individuo*/
void TIndividuo::escribirArbol(TArbol* nuevo)
{
  arbol = nuevo;
  arbol->escribirRaiz(true);
}

/*Procedimiento que cruza al propio individuo con otro dando
como resultado del cruce 2 individuos nuevos*/
void TIndividuo::cruce( TIndividuo padre2, TIndividuo* hijo1,
  TIndividuo* hijo2, int alturaMax, vector<TDatos> * datos,
  int iter, int nmax)
{
    TArbol *arbol1, *arbol2;
    bool esHi1, esHi2, raiz1, raiz2;
    TArbol* nodo1;
    TArbol* nodo2;
    TArbol* nodo_aux1;
    TArbol* pater1 = NULL;
    TArbol* pater2 = NULL;
    bool parar = false;
    int nuevaAlt1, nuevaAlt2;

    *hijo1 = TIndividuo(this);
    *hijo2 = TIndividuo(padre2);

    arbol1 = this->leerGen();
    arbol2 = padre2.leerGen();

    while (!parar)
    {
        /*Elegimos un nodo al azar de cada árbol, y guardamos su padre*/
        nodo1 = arbol1->nodoAleatorio();
        nodo2 = arbol2->nodoAleatorio();
        nodo_aux1 = nodo1;
        raiz1 = nodo1->esRaiz();
        raiz2 = nodo2->esRaiz();

        /*Necesitaremos saber si los nodos escogidos son hi o hd*/
        esHi1 = nodo1->esHijoIzquierdo();
        esHi2 = nodo2->esHijoIzquierdo();

        /*Calculamos los padres y las nuevas alturas iniciales*/
        if (!raiz1)
        {
           pater1 = nodo1->leerPadre();
           nuevaAlt1 = pater1->leerProfundidad() + 1;
        }
        else nuevaAlt1 = 0;

        if (!raiz2)
        {
           pater2 = nodo2->leerPadre();
           nuevaAlt2 = pater2->leerProfundidad() + 1;
        }
        else nuevaAlt2 = 0;

        /*La altura del nuevo árbol sólo se podrá pasar por la nueva
        rama, no por las ya existentes*/
        nuevaAlt1 = nuevaAlt1 + nodo2->altura();
        nuevaAlt2 = nuevaAlt2 + nodo1->altura();

        parar = ((nuevaAlt1 <= alturaMax) && (nuevaAlt2 <= alturaMax));
    }

    /*Si el nodo a intercambiar es la raíz es como cambiar el
     árbol entero*/
    if (raiz1)
      arbol1 = new TArbol(nodo2,NULL);
    else
    {
      if (esHi1)
        pater1->escribirHi(nodo2);
      else
        pater1->escribirHd(nodo2);
    }

    if (raiz2)
      arbol2 = new TArbol(nodo_aux1,NULL);
    else
    {
      if (esHi2)
        pater2->escribirHi(nodo_aux1);
      else
        pater2->escribirHd(nodo_aux1);
    }

    arbol1->actualizar(0);
    arbol2->actualizar(0);
    hijo1->escribirArbol(arbol1);
    hijo2->escribirArbol(arbol2);
    hijo1->adaptacion(datos,iter,nmax);
    hijo2->adaptacion(datos,iter,nmax);
}

/*Procedimiento que muta un nodo al azar del gen de un individuo*/
void TIndividuo::mutarGen(vector <TDatos> * datos, int iter, int nmax,
  vector<TFuncion>  cjtoFuns, vector<String>  cjtoTerms)
{

    TArbol* nodo;

    /*Elegimos un nodo al azar y lo mutamos*/
    nodo = arbol->nodoAleatorio();
    nodo->mutaNodo(cjtoFuns, cjtoTerms);
    adaptacion( datos, iter, nmax);
}

