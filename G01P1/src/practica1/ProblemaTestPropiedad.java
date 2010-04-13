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

import ag.Poblacion;
import ag.Seleccion;

public class ProblemaTestPropiedad extends ag.Problema {

	private double m_pasoActual = 0;
	private double m_paso = 0;
	private double m_total = 0;

	public int numMaxGen() {
		if (ventanaPrincipal().rangoSeleccionado() == 0) {
			return (int) m_pasoActual;
		} else {
			return super.numMaxGen();
		}
	}

	public int tamPoblacion() {
		if (ventanaPrincipal().rangoSeleccionado() == 3) {
			return (int) m_pasoActual;
		} else {
			return super.tamPoblacion();
		}
	}

	public double probCruce() {
		if (ventanaPrincipal().rangoSeleccionado() == 1) {
			return m_pasoActual;
		} else {
			return super.probCruce();
		}
	}

	public double probMutacion() {
		if (ventanaPrincipal().rangoSeleccionado() == 2) {
			return m_pasoActual;
		} else {
			return super.probMutacion();
		}
	}

	public double precision() {
		if (ventanaPrincipal().rangoSeleccionado() == 4) {
			return m_pasoActual;
		} else {
			return super.precision();
		}
	}

	public void lanzar(boolean limpiaAnterior) {
		String atributo = null;

		switch (ventanaPrincipal().rangoSeleccionado()) {
			case 0:
				atributo = new String("Máx. Generaciones: ");
				m_pasoActual = ventanaPrincipal().maxGen();
				m_paso = ventanaPrincipal().maxGenp();
				m_total = ventanaPrincipal().maxGen2();
				break;
			case 1:
				atributo = new String("Prob. Cruce: ");
				m_pasoActual = ventanaPrincipal().probCruce();
				m_paso = ventanaPrincipal().probCrucep();
				m_total = ventanaPrincipal().probCruce2();
				break;
			case 2:
				atributo = new String("Prob. Mutación: ");
				m_pasoActual = ventanaPrincipal().probMutacion();
				m_paso = ventanaPrincipal().probMutacionp();
				m_total = ventanaPrincipal().probMutacion2();
				break;
			case 3:
				atributo = new String("Tam. Población: ");
				m_pasoActual = ventanaPrincipal().tamPoblacion();
				m_paso = ventanaPrincipal().tamPoblacionp();
				m_total = ventanaPrincipal().tamPoblacion2();
				break;
			case 4:
				atributo = new String("Precisión: ");
				m_pasoActual = ventanaPrincipal().precision();
				m_paso = ventanaPrincipal().precisionp();
				m_total = ventanaPrincipal().precision2();
				break;
			default:
				break;
		}

		int act = 0;
		int total = (int) (((m_total - m_pasoActual)) / m_paso) + 1;

		ventanaPrincipal().progressBar().setMinimum(0);
		ventanaPrincipal().progressBar().setMaximum(total);

		ventanaPrincipal().grafica1().removeAllPlots();
		ventanaPrincipal().grafica2().removeAllPlots();
		ventanaPrincipal().grafica3().removeAllPlots();

		int k = 0;
		int mejor = -1;
		ag.Cromosoma mejorCromosoma = null;
		String resString = new String();

		while (m_pasoActual <= m_total) {
			Poblacion p = Factoria.genPoblacionVacia();
			p.genPoblacionInicial();
			p.evaluarPoblacion();
			int gen = 0;

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

				Poblacion res = Factoria.genPoblacionVacia();
				Seleccion.ruleta(p, res);
				res.cruzar();
				res.mutar();
				res.evaluarPoblacion();
				p = res;
				++gen;
			}

			++act;
			ventanaPrincipal().progressBar().setValue(act);

			if ((mejor == -1) || (getMejor().aptitud() > mejorCromosoma.aptitud())) {
				mejor = k;
				mejorCromosoma = getMejor();
			}

			ventanaPrincipal().grafica1().addLinePlot("Global (" + atributo + (float) m_pasoActual + ")", ejex, grafica1yMejorAlgoritmo);
			ventanaPrincipal().grafica1().addLinePlot("Generación Actual (" + atributo + (float) m_pasoActual + ")", ejex, grafica1yMejorGeneracion);
			ventanaPrincipal().grafica2().addLinePlot("Media por Generación (" + atributo + (float) m_pasoActual + ")", ejex, grafica2yMediaAptitud);
			ventanaPrincipal().grafica2().addLinePlot("Máxima por Generación (" + atributo + (float) m_pasoActual + ")", ejex, grafica2yMaximaAptitud);
			ventanaPrincipal().grafica3().addLinePlot("Presión Selectiva (" + atributo + (float) m_pasoActual + ")", ejex, grafica3yPresionSelectiva);

			resString += "\tIteración " + k + ":\tEl mejor es:\t" + getMejor().fenotipo();
			resString += "\n\t\tEvaluación:\t" + getMejor().evaluacion() + "\n\n";

			setMejor(null);

			m_pasoActual += m_paso;

			++k;
		}

		ventanaPrincipal().terminado();
		resString += "\tGeneral:\tEl mejor es:\t" + mejorCromosoma.fenotipo();
		resString += "\n\t\tIteración:\t" + mejor;
		resString += "\n\t\tEvaluación:\t" + mejorCromosoma.evaluacion();
		ventanaPrincipal().resultado().setText(resString);
	}

	@Override
	public int tamCromosoma() {
		switch (ventanaPrincipal().problemaSeleccionado()) {
			case 0:
				return (int) Math.ceil(Math.log(1.0 + 1.0 / precision()) / Math.log(2));
			case 1:
				return (int) Math.ceil(Math.log(1.0 + 15.1 / precision()) / Math.log(2)) +
                       (int) Math.ceil(Math.log(1.0 + (5.8 - 4.1) / precision()) / Math.log(2));
			case 2:
				return (int) Math.ceil(Math.log(1.0 + 25.0 / precision()) / Math.log(2));
			case 3:
				return (int) Math.ceil(Math.log(1.0 + 20.0 / precision()) / Math.log(2)) * 2;
			case 4:
				return (int) (Math.ceil(Math.log(1.0 + Math.PI / precision()) / Math.log(2)) * ventanaPrincipal().n());
			default:
				break;
		}
		return 0;
	}

}
