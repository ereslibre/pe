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

public class ProblemaF2 extends agsimple.Problema {

	@Override
	public int tamCromosoma() {
		return (int) Math.ceil(Math.log(1.0 + (12.1 - (-3.0)) / 0.001) / Math.log(2)) +
		       (int) Math.ceil(Math.log(1.0 + (5.8 - 4.1) / 0.001) / Math.log(2));
	}

	@Override
	public int numMaxGen() {
		return 100;
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
		return 100;
	}

	@Override
	public void lanzar() {
		PoblacionF2 p = new PoblacionF2(this);
		p.genPoblacionInicial();
		p.evaluarPoblacion();
		int gen = 0;

		ventanaPrincipal().grafica1().removeAllPlots();
		ventanaPrincipal().progressBar().setMinimum(1);
		ventanaPrincipal().progressBar().setMaximum(numMaxGen());
		double[] grafica1xMejorAlgoritmo = new double[numMaxGen()];
		double[] grafica1yMejorAlgoritmo = new double[numMaxGen()];
		double[] grafica1xMejorGeneracion = new double[numMaxGen()];
		double[] grafica1yMejorGeneracion = new double[numMaxGen()];
		while (gen < numMaxGen()) {
			grafica1xMejorAlgoritmo[gen] = gen;
			grafica1yMejorAlgoritmo[gen] = getMejor().evaluacion();
			grafica1xMejorGeneracion[gen] = gen;
			grafica1yMejorGeneracion[gen] = p.getMejor().evaluacion();

			PoblacionF2 res = new PoblacionF2(this);
			Seleccion.ruleta(p, res);
			res.cruzar();
			res.mutar();
			res.evaluarPoblacion();
			p = res;
			++gen;
			ventanaPrincipal().progressBar().setValue(gen);
		}
		ventanaPrincipal().grafica1().addLinePlot("Algoritmo", grafica1xMejorAlgoritmo, grafica1yMejorAlgoritmo);
		ventanaPrincipal().grafica1().addLinePlot("Generación", grafica1xMejorGeneracion, grafica1yMejorGeneracion);
		System.out.println("El mejor es " + getMejor().fenotipo());
		System.out.println("Evaluación es " + getMejor().evaluacion());
	}

}
