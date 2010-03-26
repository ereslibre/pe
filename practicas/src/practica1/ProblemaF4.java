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

package practica1;

import ag.Seleccion;

public class ProblemaF4 extends agsimple.Problema {

	@Override
	public int tamCromosoma() {
		return (int) Math.ceil(Math.log(1.0 + (10.0 - (-10.0)) / 0.0001) / Math.log(2)) * 2;
	}

	@Override
	public int numMaxGen() {
		return 200;
	}

	@Override
	public double probCruce() {
		return 0.8;
	}

	@Override
	public double probMutacion() {
		return 0.2;
	}

	@Override
	public int tamPoblacion() {
		return 200;
	}

	@Override
	public void lanzar() {
		PoblacionF4 p = new PoblacionF4(this);
		p.genPoblacionInicial();
		p.evaluarPoblacion();
		int gen = 0;

		ventanaPrincipal().progressBar().setMinimum(1);
		ventanaPrincipal().progressBar().setMaximum(numMaxGen());

		ventanaPrincipal().grafica1().removeAllPlots();
		ventanaPrincipal().grafica2().removeAllPlots();
		ventanaPrincipal().grafica3().removeAllPlots();
		double[] ejex = new double[numMaxGen()];
		for (int i = 0; i < numMaxGen(); ++i) {
			ejex[i] = i;
		}
		double[] grafica1yMejorAlgoritmo = new double[numMaxGen()];
		double[] grafica1yMejorGeneracion = new double[numMaxGen()];
		double[] grafica2yMediaAptitud = new double[numMaxGen()];
		double[] grafica2yMaximaAptitud = new double[numMaxGen()];
		double[] grafica3yPresionSelectiva = new double[numMaxGen()];
		while (gen < numMaxGen()) {
			grafica1yMejorAlgoritmo[gen] = getMejor().evaluacion();
			grafica1yMejorGeneracion[gen] = p.getMejor().evaluacion();
			grafica2yMediaAptitud[gen] = p.aptitudMedia();
			grafica2yMaximaAptitud[gen] = p.getMejor().aptitud();
			grafica3yPresionSelectiva[gen] = p.getMejor().aptitud() / p.aptitudMedia();

			PoblacionF4 res = new PoblacionF4(this);
			Seleccion.ruleta(p, res);
			res.cruzar();
			res.mutar();
			res.evaluarPoblacion();
			p = res;
			++gen;
			ventanaPrincipal().progressBar().setValue(gen);
		}
		ventanaPrincipal().grafica1().addLinePlot("Algoritmo", ejex, grafica1yMejorAlgoritmo);
		ventanaPrincipal().grafica1().addLinePlot("Generación", ejex, grafica1yMejorGeneracion);
		ventanaPrincipal().grafica2().addLinePlot("Aptitud Media", ejex, grafica2yMediaAptitud);
		ventanaPrincipal().grafica2().addLinePlot("Aptitud Máxima", ejex, grafica2yMaximaAptitud);
		ventanaPrincipal().grafica3().addLinePlot("Presión Selectiva", ejex, grafica3yPresionSelectiva);
		System.out.println("El mejor es " + getMejor().fenotipo());
		System.out.println("Evaluación es " + getMejor().evaluacion());
	}

}
