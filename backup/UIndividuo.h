//---------------------------------------------------------------------------

#ifndef UIndividuoH
#define UIndividuoH

#include "UArbol.h"

//---------------------------------------------------------------------------

class TIndividuo {

    /* Atributos privados de la clase */
    private:
        TArbol* arbol;
        int hmax;
        int aptitud;
        float puntuacion; //pi
        float punt_acu; //qi

        /*Función auxiliar de adaptacion()*/
        bool entraEnRango(TDatos dato, float error);

    public:

        /* Constructoras de la clase */
        TIndividuo(){};
        TIndividuo(TIndividuo* indiv);
        TIndividuo(vector<TDatos> * datos, vector<TFuncion> * cjtoFuns,
          vector<String> * cjtoTerms, int hmax, int num_max_gen);

        /* Métodos para leer las variables privadas */
        int leerAptitud(){ return aptitud; };
        float leerPuntuacion(){ return puntuacion; };
        float leerPuntAcu(){ return punt_acu; };
        TArbol* leerGen() { return arbol; };

        /* Métodos para escribir las variables privadas */
        void escribirPuntuacion(float punt) { puntuacion=punt; };
        void escribirPuntAcu(float punt) { punt_acu=punt; };
        void escribirArbol(TArbol* nuevo);

        /* Métodos propios de la clase */
        String mostrar();
        void adaptacion(vector<TDatos> * datos, int iter, int nmax);
        void cruce( TIndividuo padre2, TIndividuo* hijo1,
          TIndividuo* hijo2, int alturaMax, vector<TDatos> * datos,
          int iter, int nmax);
        void mutarGen(vector <TDatos> * datos, int iter, int nmax,
          vector<TFuncion>  cjtoFuns, vector<String>  cjtoTerms);
};

class TCompararInd
{
    public:

      /*Los individuos se ordenan por orden decreciente de aptitud*/
      bool operator () (TIndividuo i1, TIndividuo i2)
        { return i1.leerAptitud() > i2.leerAptitud(); }

};


#endif
