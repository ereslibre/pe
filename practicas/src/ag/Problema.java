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

package ag;

import vista.VentanaPrincipal;

public abstract class Problema extends Thread {

	private Cromosoma        m_mejor = null;
	private VentanaPrincipal m_ventanaPrincipal = null;

	public int numMaxGen() {
		return Integer.valueOf(m_ventanaPrincipal.maxGen());
	}

	public int tamPoblacion() {
		return Integer.valueOf(m_ventanaPrincipal.tamPoblacion());
	}

	public double probCruce() {
		return Double.valueOf(m_ventanaPrincipal.probCruce());
	}

	public double probMutacion() {
		return Double.valueOf(m_ventanaPrincipal.probMutacion());
	}

	protected abstract Poblacion genPoblacionVacia();

	public Cromosoma getMejor() {
		return m_mejor;
	}

	public void setMejor(Cromosoma mejor) {
		m_mejor = mejor;
	}

	@Override
	public void run() {
		lanzar(true);
	}

	public void lanzar(boolean limpiaAnterior) {
		Poblacion p = genPoblacionVacia();
		p.genPoblacionInicial();
		p.evaluarPoblacion();
		int gen = 0;

		if (limpiaAnterior) {
			m_ventanaPrincipal.progressBar().setMinimum(1);
			m_ventanaPrincipal.progressBar().setMaximum(numMaxGen());
			m_ventanaPrincipal.grafica1().removeAllPlots();
			m_ventanaPrincipal.grafica2().removeAllPlots();
			m_ventanaPrincipal.grafica3().removeAllPlots();
		}

		double[] ejex = new double[numMaxGen() + 1];
		for (int i = 0; i <= numMaxGen(); ++i) {
			ejex[i] = i;
		}
		double[] grafica1yMejorAlgoritmo = new double[numMaxGen() + 1];
		double[] grafica1yMejorGeneracion = new double[numMaxGen() + 1];
		double[] grafica2yMediaAptitud = new double[numMaxGen() + 1];
		double[] grafica2yMaximaAptitud = new double[numMaxGen() + 1];
		double[] grafica3yPresionSelectiva = new double[numMaxGen() + 1];

		while (gen <= numMaxGen()) {
			grafica1yMejorAlgoritmo[gen] = getMejor().evaluacion();
			grafica1yMejorGeneracion[gen] = p.getMejor().evaluacion();
			grafica2yMediaAptitud[gen] = p.aptitudMedia();
			grafica2yMaximaAptitud[gen] = p.getMejor().aptitud();
			grafica3yPresionSelectiva[gen] = p.getMejor().aptitud() / p.aptitudMedia();

			Poblacion res = genPoblacionVacia();
			Seleccion.ruleta(p, res);
			res.cruzar();
			res.mutar();
			res.evaluarPoblacion();
			p = res;
			++gen;

			if (limpiaAnterior) {
				m_ventanaPrincipal.progressBar().setValue(gen);
			}
		}
		m_ventanaPrincipal.grafica1().addLinePlot("Global", ejex, grafica1yMejorAlgoritmo);
		m_ventanaPrincipal.grafica1().addLinePlot("Generación Actual", ejex, grafica1yMejorGeneracion);
		m_ventanaPrincipal.grafica2().addLinePlot("Media por Generación", ejex, grafica2yMediaAptitud);
		m_ventanaPrincipal.grafica2().addLinePlot("Máxima por Generación", ejex, grafica2yMaximaAptitud);
		m_ventanaPrincipal.grafica3().addLinePlot("Presión Selectiva", ejex, grafica3yPresionSelectiva);

		if (limpiaAnterior) {
			m_ventanaPrincipal.terminado();
			System.out.println("El mejor es " + getMejor().fenotipo());
			System.out.println("Evaluación es " + getMejor().evaluacion());
		}
	}

	public void setVentanaPrincipal(VentanaPrincipal ventanaPrincipal) {
		m_ventanaPrincipal = ventanaPrincipal;
	}

	public VentanaPrincipal ventanaPrincipal() {
		return m_ventanaPrincipal;
	}

}
