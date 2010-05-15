//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop

#include "UArbol.h"

//---------------------------------------------------------------------------

#pragma package(smart_init)

/*Constructora de copia*/
TArbol::TArbol(TArbol* arbol, TArbol* pater)
{
    nombre = arbol->leerNombre();
    posSimbolo = arbol->leerPosSimbolo();
    hoja = arbol->esHoja();
    if (pater==NULL)
      raiz = true;
    else
      raiz = false;
    numNodos = arbol->leerNumNodos();
    profundidad = arbol->leerProfundidad();
    padre = pater;
    esHi = arbol->esHijoIzquierdo();
    if (!hoja)
    {
      hi = new TArbol(arbol->hi, this);
      if ( (nombre != "sqrt") && (nombre != "log") )
        hd = new TArbol(arbol->hd, this);
      else
        hd = NULL;
    }
}

/*Constructora que genera un �rbol aleatorio de profundidad m�xima hmax sabiendo que
actualmente estamos en profundidad prof*/
TArbol::TArbol(vector<TFuncion> * cjtoFuns,  vector<String> *  cjtoTerms,
  int hmax, int prof, TArbol* pater, bool esHizq, bool esRaiz)
{
    float rnd;
    int nuevaProf = prof+1;

    /*Creamos un �rbol que en los nodos tendr� elementos de cjtoFuns
    y en las hojas tendr� elementos de cjtoTerms*/
    /*Miramos si va a ser hoja o nodo interno*/
    rnd = rand() % 2;
    escribirProfundidad(prof);
    padre = pater;
    esHi = esHizq;
    raiz = esRaiz;

    /*Por lo menos hay 1 nodo, que es �l mismo*/
    numNodos = 1;
    if ((rnd == 0) || (profundidad + 1 == hmax))
    {
      /*Ponemos una hoja*/
      rnd = rand() % cjtoTerms->size();
      nombre = cjtoTerms->at(rnd);
      hoja = true;
    }
    else
    {
      /*Creamos sus hijos*/
      rnd = rand() % cjtoFuns->size();
      nombre = cjtoFuns->at(rnd).nombreFun;
      hi = new TArbol(cjtoFuns, cjtoTerms, hmax, nuevaProf, this, true, false);
      numNodos = numNodos + hi->leerNumNodos();
      /*Si la funci�n tiene aridad 1, el �rbol solo tendr� hijo izquierdo*/
      if (cjtoFuns->at(rnd).aridad == 1)
         hd = NULL;
      else
      {
         hd =  new TArbol(cjtoFuns, cjtoTerms, hmax, nuevaProf, this, false, false);
         numNodos = numNodos + hd->leerNumNodos();
      }
      hoja = false;
    }
    posSimbolo = rnd;
}

/*Sustituye el hijo derecho por nodo*/
void TArbol::escribirHd(TArbol* nodo)
{
  hd = new TArbol(nodo,this);
  hd->raiz = false;
  hd->esHi = false;
  hoja = false;
}

/*Sustituye el hijo izquierdo por nodo*/
void TArbol::escribirHi(TArbol* nodo)
{
  hi = new TArbol(nodo,this);
  hi->escribirPadre(this);
  hi->raiz = false;
  hi->esHi = true;
  hoja = false;
}

/*Actualiza la profundidad y el n�mero de nodos de todo el �rbol*/
void TArbol::actualizar(int prof)
{
  int nuevaProf = prof+1;

  escribirProfundidad(prof);
  if (esHoja())
    numNodos = 1;
  else
  {
    hi->actualizar(nuevaProf);
    numNodos = 1 + hi->leerNumNodos();
    if ( (nombre != "sqrt") && (nombre != "log") )
    {
      hd->actualizar(nuevaProf);
      numNodos = numNodos + hd->leerNumNodos();
    }
  }

}

/*Devuelve el valor de evaluar la expresi�n del �rbol con A=a*/
float TArbol::evaluar( float a )
{
  float resi, resd, res=0;

  if (esHoja())
    return a;
  else
  {
    resi = hi->evaluar(a);

    if (nombre == "sqrt")
    {
        if (resi<0)
          res = -MaxInt;
        else
          res = sqrt(resi);
    }
    else if ( nombre == "log" )
    {
        if ( resi <= 0 )
          res = 0;
        else
          res = log(resi);
    }
    else
    {
      resd = hd->evaluar(a);

      if (nombre == "*")
        res = resi*resd;

      else if (nombre == "/")
      {
        if (resd != 0)
          res = resi/resd;
        else
          res = -MaxInt;
      }

      else if (nombre == "+")
        res = resi+resd;

      else if (nombre == "-")
        res = resi-resd;
    }
  }
  return res;
}

/*Devuelve un puntero a un nodo escogido aleatoriamente*/
TArbol* TArbol::nodoAleatorio()
{
  float rnd;
  TArbol* nodo;

  rnd = rand() % leerNumNodos();

  /*Le sumamos 1 pq numNodos queremos que vaya de 1 a N y el
  random va de 0 a N-1*/
  rnd = rnd + 1;

  nodo = buscarNodo(rnd);
  return nodo;
}

/*Devuelve el nodo n-�simo del �rbol*/
TArbol* TArbol::buscarNodo(int n)
{
  int medio;

  if (esHoja() && (n == 1))
    return this;
  else if (!esHoja())
  {
      medio =(hi->leerNumNodos() +1);
      if (n < medio)
        return hi->buscarNodo(n);
      else if (n==medio)
        return this;
      else
        return hd->buscarNodo(n - medio);
  }
  else
  {
    ShowMessage("Error al buscar el nodo");
    return NULL;
  }
}

/*Muta el s�mbolo del �rbol*/
void TArbol::mutaNodo( vector<TFuncion>  cjtoFuns, vector<String>  cjtoTerms)
{
   int rnd;

   if (esHoja())
   {
        /*Si hay m�s de un s�mbolo terminal mutamos*/
        if ( cjtoTerms.size() > 1 )
        {
          rnd = leerPosSimbolo();
          while (rnd == leerPosSimbolo())
          {
             rnd = rand() % cjtoTerms.size();
          }
          if (rnd != leerPosSimbolo())
          {
            posSimbolo = rnd;
            nombre = cjtoTerms.at(rnd);
          }
        }
   }
   else if (!(esHoja()))
   {
        /*Si hay m�s de una funci�n mutamos*/
        if ( cjtoFuns.size() > 1 )
        {
          //rnd = leerPosSimbolo();
          rnd = rand() % cjtoFuns.size();
          //while (rnd == leerPosSimbolo())
          while(cjtoFuns.at(leerPosSimbolo()).aridad != cjtoFuns.at(rnd).aridad)
          {
             rnd = rand() % cjtoFuns.size();
          }
          //if (cjtoFuns.at(leerPosSimbolo()).aridad == cjtoFuns.at(rnd).aridad)
          if (rnd != leerPosSimbolo())
          {
            /*Si no es el mismo operador y tienen la misma aridad*/
            /*ShowMessage((AnsiString)"Cambio " + cjtoFuns.at(posSimbolo).nombreFun
               + (AnsiString)"por " + cjtoFuns.at(rnd).nombreFun);  */
            posSimbolo = rnd;
            nombre = cjtoFuns.at(rnd).nombreFun;
          }
        }
   }
}

/*Muestra el �rbol en una String*/
String TArbol::mostrar()
{
    String cad;

    cad = nombre;
    if (!esHoja())
    {
        cad = cad + "(" + hi->mostrar();
        if (hd!=NULL)
          cad = cad + "," + hd->mostrar();
        cad = cad + ")";
    }
    return cad;
}

/*Devuelve la altura del �rbol*/
int TArbol::altura()
{
    int alt = 0;
    int altHi = 0;
    int altHd = 0;

    if (!esHoja())
    {
        altHi = leerHi()->altura();
        if (leerHd())
        {
            altHd = leerHd()->altura();
        }
        if ( altHi > altHd )
          alt = 1 + altHi;
        else
          alt = 1 + altHd;
    }
    else
      alt = 1;

    return alt;
}
