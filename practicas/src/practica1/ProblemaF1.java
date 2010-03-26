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

public class ProblemaF1 extends agsimple.Problema {

	@Override
	public int tamCromosoma() {
		return (int) Math.ceil(Math.log(1.0 + (1.0 - 0) / 0.00000001) / Math.log(2));
	}

	@Override
	public int numMaxGen() {
		return 30;
	}

	@Override
	public double probCruce() {
		return 0.6;
	}

	@Override
	public double probMutacion() {
		return 0.05;
	}

	@Override
	public int tamPoblacion() {
		return 100;
	}

	@Override
	public void lanzar() {
		Poblacion p = new Poblacion(this);
		p.genPoblacionInicial();
		p.evaluarPoblacion();
		int gen = 0;

		ventanaPrincipal().progressBar().setMinimum(0);
		ventanaPrincipal().progressBar().setMaximum(numMaxGen() - 1);
		while (gen < numMaxGen()) {
			Poblacion res = new Poblacion(this);
			Seleccion.ruleta(p, res);
			res.cruzar();
			res.mutar();
			res.evaluarPoblacion();
			p = res;
			ventanaPrincipal().progressBar().setValue(gen);
			++gen;
		}
		System.out.println("El mejor es " + p.getMejor().fenotipo());
		System.out.println("Aptitud es " + p.getMejor().aptitud());
	}

}
