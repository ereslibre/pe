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

import java.util.ArrayList;

import ag.Cruce;

public class CromosomaF2 extends agsimple.Cromosoma {

	CromosomaF2(PoblacionF2 poblacion) {
		super(poblacion);
	}

	@SuppressWarnings("unchecked")
	@Override
	public double aptitud() {
		final ArrayList<Double> components = (ArrayList<Double>) fenotipo();
		final Double x = components.get(0);
		final Double y = components.get(1);
		return 21.5 + x * Math.sin(4.0 * Math.PI * x) + y * Math.sin(20.0 * Math.PI * y);
	}

	@Override
	public Object clone() {
		CromosomaF2 res = new CromosomaF2((PoblacionF2) m_poblacion);
		res.m_madre = m_madre;
		res.m_padre = m_padre;
		res.m_cromosoma = new boolean[m_cromosoma.length];
		for (int i = 0; i < m_cromosoma.length; ++i) {
			res.m_cromosoma[i] = m_cromosoma[i];
		}
		return res;
	}

	@Override
	public Cruce cruzar(ag.Cromosoma cromosoma) {
		final int tamCromosoma = ((agsimple.Problema) poblacion().problema()).tamCromosoma();
		final int tamx = ((int) Math.ceil(Math.log(1.0 + (12.1 - (-3.0)) / poblacion().problema().precision()) / Math.log(2)));
		final int posCruceGen1 = (int) (Math.random() * (Double) Math.ceil(Math.log(1.0 + (12.1 - (-3.0)) / poblacion().problema().precision()) / Math.log(2)));
		final int posCruceGen2 = (int) (Math.random() * (Double) Math.ceil(Math.log(1.0 + (5.8 - 4.1) / poblacion().problema().precision()) / Math.log(2)));

		Double ellaOYo = Math.random();

		CromosomaF2 hijo1 = (CromosomaF2) poblacion().genCromosomaVacio();
		boolean hijo1c[] = new boolean[tamCromosoma];
		{
			for (int i = 0; i <= posCruceGen1; ++i)  {
				hijo1c[i] = ellaOYo <= 0.5 ? ((boolean[]) genotipo())[i] : ((boolean[]) cromosoma.genotipo())[i];
			}
			for (int i = posCruceGen1 + 1; i < tamx; ++i) {
				hijo1c[i] = ellaOYo <= 0.5 ? ((boolean[]) cromosoma.genotipo())[i] : ((boolean[]) genotipo())[i];
			}
			for (int i = tamx; i <= tamx + posCruceGen2; ++i) {
				hijo1c[i] = ellaOYo <= 0.5 ? ((boolean[]) genotipo())[i] : ((boolean[]) cromosoma.genotipo())[i];
			}
			for (int i = tamx + posCruceGen2 + 1; i < tamCromosoma; ++i) {
				hijo1c[i] = ellaOYo <= 0.5 ? ((boolean[]) cromosoma.genotipo())[i] : ((boolean[]) genotipo())[i];
			}
		}
		hijo1.setCromosoma(hijo1c);
		hijo1.setMadre(this);
		hijo1.setPadre(cromosoma);

		CromosomaF2 hijo2 = (CromosomaF2) poblacion().genCromosomaVacio();
		boolean hijo2c[] = new boolean[tamCromosoma];
		{
			for (int i = 0; i <= posCruceGen1; ++i)  {
				hijo2c[i] = ellaOYo <= 0.5 ? ((boolean[]) cromosoma.genotipo())[i] : ((boolean[]) genotipo())[i];
			}
			for (int i = posCruceGen1 + 1; i < tamx; ++i) {
				hijo2c[i] = ellaOYo <= 0.5 ? ((boolean[]) genotipo())[i] : ((boolean[]) cromosoma.genotipo())[i];
			}
			for (int i = tamx; i <= tamx + posCruceGen2; ++i) {
				hijo2c[i] = ellaOYo <= 0.5 ? ((boolean[]) cromosoma.genotipo())[i] : ((boolean[]) genotipo())[i];
			}
			for (int i = tamx + posCruceGen2 + 1; i < tamCromosoma; ++i) {
				hijo2c[i] = ellaOYo <= 0.5 ? ((boolean[]) genotipo())[i] : ((boolean[]) cromosoma.genotipo())[i];
			}
		}
		hijo2.setCromosoma(hijo2c);
		hijo2.setMadre(this);
		hijo2.setPadre(cromosoma);

		return new Cruce(hijo1, hijo2);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean esFactible() {
		final ArrayList<Double> components = (ArrayList<Double>) fenotipo();
		final Double x = components.get(0);
		final Double y = components.get(1);
		return x >= -3.0 && x <= 12.1 && y >= 4.1 && y <= 5.8;
	}

	@SuppressWarnings("unchecked")
	@Override
	public double evaluacion() {
		final ArrayList<Double> components = (ArrayList<Double>) fenotipo();
		final Double x = components.get(0);
		final Double y = components.get(1);
		return 21.5 + x * Math.sin(4.0 * Math.PI * x) + y * Math.sin(20.0 * Math.PI * y);
	}

	@Override
	public Object fenotipo() {
		ArrayList<Double> res = new ArrayList<Double>();
		final int tamx = (int) Math.ceil(Math.log(1.0 + (12.1 - (-3.0)) / 0.001) / Math.log(2));
		{
			boolean[] cx = new boolean[tamx];
			for (int i = 0; i < tamx; ++i) {
				cx[i] = m_cromosoma[i];
			}
			res.add(-3.0 + bin2dec(cx) * ((12.1 - (-3.0)) / (Math.pow(2, tamx) - 1.0)));
		}
		{
			final int tamy = (int) Math.ceil(Math.log(1.0 + (5.8 - 4.1) / 0.001) / Math.log(2));
			boolean[] cy = new boolean[tamy];
			for (int i = 0; i < tamy; ++i) {
				cy[i] = m_cromosoma[i + tamx];
			}
			res.add(4.1 + bin2dec(cy) * ((5.8 - 4.1) / (Math.pow(2, tamy) - 1.0)));
		}
		return res;
	}

	public Double bin2dec(boolean[] m_cromosoma) {
		Double res = 0.0;
		for (int i = 0; i < m_cromosoma.length; ++i) {
			res += m_cromosoma[i] ? Math.pow(2, i) : 0;
		}
		return res;
	}

	@Override
	public Object genotipo() {
		return m_cromosoma;
	}

	@Override
	public void mutar() {
		boolean genotipo[] = (boolean[]) genotipo();
		for (int i = 0; i < ((agsimple.Problema) poblacion().problema()).tamCromosoma(); ++i) {
			if (Math.random() < poblacion().problema().probMutacion()) {
				genotipo[i] = !genotipo[i];
			}
		}
	}

}
