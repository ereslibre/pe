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

package vista;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;

import org.math.plot.Plot2DPanel;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTabbedPane   m_tabbedPane = new JTabbedPane();
	private Plot2DPanel   m_grafica1 = new Plot2DPanel();
	private Plot2DPanel   m_grafica2 = new Plot2DPanel();
	private Plot2DPanel   m_grafica3 = new Plot2DPanel();
	private JComboBox     m_problema = new JComboBox();
	private JProgressBar  m_progressBar = new JProgressBar();

	VentanaPrincipal() {
		super();
		m_tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		m_tabbedPane.addTab("Problema", problema());
		m_tabbedPane.addTab("Algoritmo", generaGrafica1());
		m_tabbedPane.addTab("Aptitud", generaGrafica2());
		m_tabbedPane.addTab("Presión Selectiva", generaGrafica3());
		setLayout(new BorderLayout());
		add(m_tabbedPane, BorderLayout.CENTER);
		add(m_progressBar, BorderLayout.SOUTH);
		setSize(640, 480);
		setLocationRelativeTo(getRootPane());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public JProgressBar progressBar() {
		return m_progressBar;
	}

	public Plot2DPanel grafica1() {
		return m_grafica1;
	}

	public Plot2DPanel grafica2() {
		return m_grafica2;
	}

	public Plot2DPanel grafica3() {
		return m_grafica3;
	}

	JComponent problema() {
        JPanel p = new JPanel();
		p.setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(3, 3, 3, 3);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;

		gridBagConstraints.gridwidth = 5;
		final String[] nombreProblemas = { "Problema 1", "Problema 2", "Problema 3", "Problema 4", "Problema 5"};
		m_problema = new JComboBox(nombreProblemas);
		p.add(m_problema, gridBagConstraints);

		gridBagConstraints.gridy = 1;
		p.add(new JCheckBox("Rangos"), gridBagConstraints);

		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.gridy = 2;
		p.add(new JRadioButton(), gridBagConstraints);

		gridBagConstraints.gridx = 1;
		p.add(new JLabel("Tamaño de Población"), gridBagConstraints);

		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridx = 0;
		p.add(new JRadioButton(), gridBagConstraints);

		gridBagConstraints.gridx = 1;
		p.add(new JLabel("Número Máximo de Generaciones"), gridBagConstraints);

		gridBagConstraints.gridy = 4;
		gridBagConstraints.gridx = 0;
		p.add(new JRadioButton(), gridBagConstraints);

		gridBagConstraints.gridx = 1;
		p.add(new JLabel("Porcentaje de Cruce"), gridBagConstraints);

		gridBagConstraints.gridy = 5;
		gridBagConstraints.gridx = 0;
		p.add(new JRadioButton(), gridBagConstraints);

		gridBagConstraints.gridx = 1;
		p.add(new JLabel("Porcentaje de Mutación"), gridBagConstraints);

		gridBagConstraints.gridy = 6;
		gridBagConstraints.gridx = 0;
		p.add(new JRadioButton(), gridBagConstraints);

		gridBagConstraints.gridx = 1;
		p.add(new JLabel("Precisión"), gridBagConstraints);

		gridBagConstraints.gridy = 7;
		gridBagConstraints.gridx = 0;
		JButton lanzar = new JButton("Lanzar");
		p.add(lanzar, gridBagConstraints);

		final VentanaPrincipal v = this;
		lanzar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				switch (m_problema.getSelectedIndex()) {
					case 0: {
						practica1.ProblemaF1 problema = new practica1.ProblemaF1();
						problema.setVentanaPrincipal(v);
						problema.start();
						break;
					}
					case 1: {
						practica1.ProblemaF2 problema = new practica1.ProblemaF2();
						problema.setVentanaPrincipal(v);
						problema.start();
						break;
					}
					case 2: {
						practica1.ProblemaF3 problema = new practica1.ProblemaF3();
						problema.setVentanaPrincipal(v);
						problema.start();
						break;
					}
					case 3: {
						practica1.ProblemaF4 problema = new practica1.ProblemaF4();
						problema.setVentanaPrincipal(v);
						problema.start();
						break;
					}
					case 4: {
						practica1.ProblemaF5 problema = new practica1.ProblemaF5();
						problema.setVentanaPrincipal(v);
						problema.start();
						break;
					}
					default:
						break;
				}
			}
        });

		return p;
	}

	JComponent generaGrafica1() {
		m_grafica1.setAxisLabel(0, "Generación");
		m_grafica1.setAxisLabel(1, "Máximo/Mínimo");
		m_grafica1.setLegendOrientation(Plot2DPanel.EAST);
		return m_grafica1;
	}

	JComponent generaGrafica2() {
		m_grafica2.setAxisLabel(0, "Generación");
		m_grafica2.setAxisLabel(1, "Media/Máxima");
		m_grafica2.setLegendOrientation(Plot2DPanel.EAST);
		return m_grafica2;
	}

	JComponent generaGrafica3() {
		m_grafica3.setAxisLabel(0, "Generación");
		m_grafica3.setAxisLabel(1, "Presión selectiva");
		m_grafica3.setLegendOrientation(Plot2DPanel.EAST);
		return m_grafica3;
	}

}
