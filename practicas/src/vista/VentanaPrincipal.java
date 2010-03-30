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
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.math.plot.Plot2DPanel;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTabbedPane  m_tabbedPane        = new JTabbedPane();
	private Plot2DPanel  m_grafica1          = new Plot2DPanel();
	private Plot2DPanel  m_grafica2          = new Plot2DPanel();
	private Plot2DPanel  m_grafica3          = new Plot2DPanel();
	private JComboBox    m_problema          = new JComboBox();
	private JProgressBar m_progressBar       = new JProgressBar();
	private JTextField   m_maxGen            = new JTextField();
	private JTextField   m_probCruce         = new JTextField();
	private JTextField   m_probMutacion      = new JTextField();
	private JTextField   m_tamPoblacion      = new JTextField();
	private JTextField   m_precision         = new JTextField();
	private JTextField   m_maxGen2           = new JTextField();
	private JTextField   m_probCruce2        = new JTextField();
	private JTextField   m_probMutacion2     = new JTextField();
	private JTextField   m_tamPoblacion2     = new JTextField();
	private JTextField   m_precision2        = new JTextField();
	private JTextField   m_maxGenp           = new JTextField();
	private JTextField   m_probCrucep        = new JTextField();
	private JTextField   m_probMutacionp     = new JTextField();
	private JTextField   m_tamPoblacionp     = new JTextField();
	private JTextField   m_precisionp        = new JTextField();
	private JRadioButton m_maxGenRadio       = new JRadioButton();
	private JRadioButton m_probCruceRadio    = new JRadioButton();
	private JRadioButton m_probMutacionRadio = new JRadioButton();
	private JRadioButton m_tamPoblacionRadio = new JRadioButton();
	private JRadioButton m_precisionRadio    = new JRadioButton();
	private JLabel       m_prob5NLabel       = new JLabel("N (Problema 5)");
	private JTextField   m_prob5N            = new JTextField();
	private JButton      m_lanzar            = new JButton();
	private JLabel       m_de                = new JLabel("De");
	private JLabel       m_a                 = new JLabel("A");
	private JLabel       m_paso              = new JLabel("Paso");
	private JPanel       m_panelPrincipal    = null;
	private JCheckBox    m_rangos            = new JCheckBox("Rangos");
	private JTextArea    m_resultado         = new JTextArea();

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

		m_maxGen.setText("100");
		m_probCruce.setText("0.6");
		m_probMutacion.setText("0.1");
		m_tamPoblacion.setText("100");
		m_precision.setText("0.0000001");

		m_maxGen2.setText("200");
		m_maxGen2.setVisible(false);
		m_probCruce2.setText("0.8");
		m_probCruce2.setVisible(false);
		m_probMutacion2.setText("0.2");
		m_probMutacion2.setVisible(false);
		m_tamPoblacion2.setText("200");
		m_tamPoblacion2.setVisible(false);
		m_precision2.setText("0.000001");
		m_precision2.setVisible(false);

		m_maxGenp.setText("25");
		m_maxGenp.setVisible(false);
		m_probCrucep.setText("0.05");
		m_probCrucep.setVisible(false);
		m_probMutacionp.setText("0.025");
		m_probMutacionp.setVisible(false);
		m_tamPoblacionp.setText("25");
		m_tamPoblacionp.setVisible(false);
		m_precisionp.setText("0.000000025");
		m_precisionp.setVisible(false);

		m_maxGenRadio.setVisible(false);
		m_probCruceRadio.setVisible(false);
		m_probMutacionRadio.setVisible(false);
		m_tamPoblacionRadio.setVisible(false);
		m_precisionRadio.setVisible(false);

		m_de.setVisible(false);
		m_a.setVisible(false);
		m_paso.setVisible(false);

		m_prob5NLabel.setVisible(false);
		m_prob5N.setText("1");
		m_prob5N.setVisible(false);

		ButtonGroup group = new ButtonGroup();
		group.add(m_maxGenRadio);
		group.add(m_probCruceRadio);
		group.add(m_probMutacionRadio);
		group.add(m_tamPoblacionRadio);
		group.add(m_precisionRadio);

		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				m_maxGen2.setEnabled(m_maxGenRadio.isSelected());
				m_maxGenp.setEnabled(m_maxGenRadio.isSelected());
				m_probCruce2.setEnabled(m_probCruceRadio.isSelected());
				m_probCrucep.setEnabled(m_probCruceRadio.isSelected());
				m_probMutacion2.setEnabled(m_probMutacionRadio.isSelected());
				m_probMutacionp.setEnabled(m_probMutacionRadio.isSelected());
				m_tamPoblacion2.setEnabled(m_tamPoblacionRadio.isSelected());
				m_tamPoblacionp.setEnabled(m_tamPoblacionRadio.isSelected());
				m_precision2.setEnabled(m_precisionRadio.isSelected());
				m_precisionp.setEnabled(m_precisionRadio.isSelected());
				m_resultado.setText("\t(Resultado no generado)");
			}
		};

		m_maxGenRadio.addActionListener(listener);
		m_probCruceRadio.addActionListener(listener);
		m_probMutacionRadio.addActionListener(listener);
		m_tamPoblacionRadio.addActionListener(listener);
		m_precisionRadio.addActionListener(listener);

		m_resultado.setOpaque(false);
		m_resultado.setEditable(false);
		m_resultado.setBackground(new Color(0,0,0,0));
		m_resultado.setText("\t(Resultado no generado)");

		m_tamPoblacionRadio.setSelected(true);
		trataIntervalos();
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

	public int maxGen() {
		return Integer.valueOf(m_maxGen.getText());
	}

	public int maxGen2() {
		return Integer.valueOf(m_maxGen2.getText());
	}

	public int maxGenp() {
		return Integer.valueOf(m_maxGenp.getText());
	}

	public double probCruce() {
		return Double.valueOf(m_probCruce.getText());
	}

	public double probCruce2() {
		return Double.valueOf(m_probCruce2.getText());
	}

	public double probCrucep() {
		return Double.valueOf(m_probCrucep.getText());
	}

	public double probMutacion() {
		return Double.valueOf(m_probMutacion.getText());
	}

	public double probMutacion2() {
		return Double.valueOf(m_probMutacion2.getText());
	}

	public double probMutacionp() {
		return Double.valueOf(m_probMutacionp.getText());
	}

	public int tamPoblacion() {
		return Integer.valueOf(m_tamPoblacion.getText());
	}

	public int tamPoblacion2() {
		return Integer.valueOf(m_tamPoblacion2.getText());
	}

	public int tamPoblacionp() {
		return Integer.valueOf(m_tamPoblacionp.getText());
	}

	public double precision() {
		return Double.valueOf(m_precision.getText());
	}

	public double precision2() {
		return Double.valueOf(m_precision2.getText());
	}

	public double precisionp() {
		return Double.valueOf(m_precisionp.getText());
	}

	public double n() {
		return Double.valueOf(m_prob5N.getText());
	}

	public JTextArea resultado() {
		return m_resultado;
	}

	public void terminado() {
		activaODesactivaTodo(m_panelPrincipal, true);
		trataIntervalos();
	}

	public int problemaSeleccionado() {
		return m_problema.getSelectedIndex();
	}

	public void activaODesactivaTodo(Component c, boolean activa) {
		c.setEnabled(activa);
		if (c instanceof Container) {
			Component [] arr = ((Container) c).getComponents();
			for (int j = 0;j < arr.length; j++) {
				activaODesactivaTodo(arr[j], activa);
			}
		}
	}

	public int rangoSeleccionado() {
		if (m_maxGenRadio.isSelected()) {
			return 0;
		}
		if (m_probCruceRadio.isSelected()) {
			return 1;
		}
		if (m_probMutacionRadio.isSelected()) {
			return 2;
		}
		if (m_tamPoblacionRadio.isSelected()) {
			return 3;
		}
		return 4;
	}

	public ArrayList<Double> rango() {
		ArrayList<Double> res = new ArrayList<Double>();
		double i = 0;
		switch (rangoSeleccionado()) {
			case 0:
				i = maxGen();
				while (i <= maxGen2()) {
					res.add(i);
					i += maxGenp();
				}
				break;
			case 1:
				i = probCruce();
				while (i <= probCruce2()) {
					res.add(i);
					i += probCruce2();
				}
				break;
			case 2:
				i = probMutacion();
				while (i <= probMutacion2()) {
					res.add(i);
					i += probMutacionp();
				}
				break;
			case 3:
				i = tamPoblacion();
				while (i <= tamPoblacion2()) {
					res.add(i);
					i += tamPoblacionp();
				}
				break;
			case 4:
				i = precision();
				while (i <= precision2()) {
					res.add(i);
					i += precisionp();
				}
				break;
			 default:
				 break;
		}
		return res;
	}

	private void trataIntervalos() {
		m_maxGen2.setEnabled(m_maxGenRadio.isSelected());
		m_maxGenp.setEnabled(m_maxGenRadio.isSelected());
		m_probCruce2.setEnabled(m_probCruceRadio.isSelected());
		m_probCrucep.setEnabled(m_probCruceRadio.isSelected());
		m_probMutacion2.setEnabled(m_probMutacionRadio.isSelected());
		m_probMutacionp.setEnabled(m_probMutacionRadio.isSelected());
		m_tamPoblacion2.setEnabled(m_tamPoblacionRadio.isSelected());
		m_tamPoblacionp.setEnabled(m_tamPoblacionRadio.isSelected());
		m_precision2.setEnabled(m_precisionRadio.isSelected());
		m_precisionp.setEnabled(m_precisionRadio.isSelected());
		m_prob5N.setVisible(m_problema.getSelectedIndex() == 4);
		m_prob5NLabel.setVisible(m_problema.getSelectedIndex() == 4);
	}

	private JComponent problema() {
        JPanel p = new JPanel();
		p.setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 10;
		gridBagConstraints.insets = new Insets(3, 3, 3, 3);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;

		gridBagConstraints.gridwidth = 5;
		final String[] nombreProblemas = { "Problema 1", "Problema 2", "Problema 3", "Problema 4", "Problema 5" };
		m_problema = new JComboBox(nombreProblemas);
		p.add(m_problema, gridBagConstraints);

		m_problema.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				m_prob5N.setVisible(m_problema.getSelectedIndex() == 4);
				m_prob5NLabel.setVisible(m_problema.getSelectedIndex() == 4);
				m_resultado.setText("\t(Resultado no generado)");
			}
        });

		gridBagConstraints.gridy = 1;
		m_rangos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				m_maxGen2.setVisible(m_rangos.isSelected());
				m_probCruce2.setVisible(m_rangos.isSelected());
				m_probMutacion2.setVisible(m_rangos.isSelected());
				m_tamPoblacion2.setVisible(m_rangos.isSelected());
				m_precision2.setVisible(m_rangos.isSelected());
				m_maxGenp.setVisible(m_rangos.isSelected());
				m_probCrucep.setVisible(m_rangos.isSelected());
				m_probMutacionp.setVisible(m_rangos.isSelected());
				m_tamPoblacionp.setVisible(m_rangos.isSelected());
				m_precisionp.setVisible(m_rangos.isSelected());
				m_maxGenRadio.setVisible(m_rangos.isSelected());
				m_probCruceRadio.setVisible(m_rangos.isSelected());
				m_probMutacionRadio.setVisible(m_rangos.isSelected());
				m_tamPoblacionRadio.setVisible(m_rangos.isSelected());
				m_precisionRadio.setVisible(m_rangos.isSelected());
				m_de.setVisible(m_rangos.isSelected());
				m_a.setVisible(m_rangos.isSelected());
				m_paso.setVisible(m_rangos.isSelected());
				m_resultado.setText("\t(Resultado no generado)");
			}
        });
		p.add(m_rangos, gridBagConstraints);

		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridx = 2;
		p.add(m_de, gridBagConstraints);
		gridBagConstraints.gridx = 3;
		p.add(m_a, gridBagConstraints);
		gridBagConstraints.gridx = 4;
		p.add(m_paso, gridBagConstraints);

		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridx = 0;
		p.add(m_tamPoblacionRadio, gridBagConstraints);

		gridBagConstraints.gridx = 1;
		p.add(new JLabel("Tamaño de Población"), gridBagConstraints);

		gridBagConstraints.gridx = 2;
		p.add(m_tamPoblacion, gridBagConstraints);

		gridBagConstraints.gridx = 3;
		p.add(m_tamPoblacion2, gridBagConstraints);

		gridBagConstraints.gridx = 4;
		p.add(m_tamPoblacionp, gridBagConstraints);

		gridBagConstraints.gridy = 4;
		gridBagConstraints.gridx = 0;
		p.add(m_maxGenRadio, gridBagConstraints);

		gridBagConstraints.gridx = 1;
		p.add(new JLabel("Número Máximo de Generaciones"), gridBagConstraints);

		gridBagConstraints.gridx = 2;
		p.add(m_maxGen, gridBagConstraints);

		gridBagConstraints.gridx = 3;
		p.add(m_maxGen2, gridBagConstraints);

		gridBagConstraints.gridx = 4;
		p.add(m_maxGenp, gridBagConstraints);

		gridBagConstraints.gridy = 5;
		gridBagConstraints.gridx = 0;
		p.add(m_probCruceRadio, gridBagConstraints);

		gridBagConstraints.gridx = 1;
		p.add(new JLabel("Probabilidad de Cruce"), gridBagConstraints);

		gridBagConstraints.gridx = 2;
		p.add(m_probCruce, gridBagConstraints);

		gridBagConstraints.gridx = 3;
		p.add(m_probCruce2, gridBagConstraints);

		gridBagConstraints.gridx = 4;
		p.add(m_probCrucep, gridBagConstraints);

		gridBagConstraints.gridy = 6;
		gridBagConstraints.gridx = 0;
		p.add(m_probMutacionRadio, gridBagConstraints);

		gridBagConstraints.gridx = 1;
		p.add(new JLabel("Probabilidad de Mutación"), gridBagConstraints);

		gridBagConstraints.gridx = 2;
		p.add(m_probMutacion, gridBagConstraints);

		gridBagConstraints.gridx = 3;
		p.add(m_probMutacion2, gridBagConstraints);

		gridBagConstraints.gridx = 4;
		p.add(m_probMutacionp, gridBagConstraints);

		gridBagConstraints.gridy = 7;
		gridBagConstraints.gridx = 0;
		p.add(m_precisionRadio, gridBagConstraints);

		gridBagConstraints.gridx = 1;
		p.add(new JLabel("Precisión"), gridBagConstraints);

		gridBagConstraints.gridx = 2;
		p.add(m_precision, gridBagConstraints);

		gridBagConstraints.gridx = 3;
		p.add(m_precision2, gridBagConstraints);

		gridBagConstraints.gridx = 4;
		p.add(m_precisionp, gridBagConstraints);

		gridBagConstraints.gridy = 8;
		gridBagConstraints.gridx = 1;
		p.add(m_prob5NLabel, gridBagConstraints);

		gridBagConstraints.gridx = 2;
		p.add(m_prob5N, gridBagConstraints);

		gridBagConstraints.gridy = 9;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
		p.add(new JLabel("Resultado:"), gridBagConstraints);

		gridBagConstraints.gridy = 10;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.weighty = 10;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
		p.add(m_resultado, gridBagConstraints);

		gridBagConstraints.gridy = 11;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.weighty = 0;
		m_lanzar = new JButton("Lanzar");
		p.add(m_lanzar, gridBagConstraints);

		final VentanaPrincipal v = this;
		m_lanzar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				m_progressBar.setValue(0);
				m_resultado.setText("\tGenerando resultado...");
				activaODesactivaTodo(v.m_panelPrincipal, false);
				ag.Problema p = null;
				if (!m_rangos.isSelected()) {
					switch (m_problema.getSelectedIndex()) {
						case 0: {
							p = new practica1.ProblemaF1();
							break;
						}
						case 1: {
							p = new practica1.ProblemaF2();
							break;
						}
						case 2: {
							p = new practica1.ProblemaF3();
							break;
						}
						case 3: {
							p = new practica1.ProblemaF4();
							break;
						}
						case 4: {
							p = new practica1.ProblemaF5();
							break;
						}
						default:
							break;
					}
				} else {
					p = new practica1.ProblemaTestPropiedad();
				}
				p.setVentanaPrincipal(v);
				p.start();
			}
        });

		m_panelPrincipal = p;
		return p;
	}

	JComponent generaGrafica1() {
		m_grafica1.setAxisLabel(0, "Generación");
		m_grafica1.setAxisLabel(1, "Evaluación");
		m_grafica1.setLegendOrientation(Plot2DPanel.EAST);
		return m_grafica1;
	}

	JComponent generaGrafica2() {
		m_grafica2.setAxisLabel(0, "Generación");
		m_grafica2.setAxisLabel(1, "Aptitud");
		m_grafica2.setLegendOrientation(Plot2DPanel.EAST);
		return m_grafica2;
	}

	JComponent generaGrafica3() {
		m_grafica3.setAxisLabel(0, "Generación");
		m_grafica3.setAxisLabel(1, "Presión Selectiva");
		m_grafica3.setLegendOrientation(Plot2DPanel.EAST);
		return m_grafica3;
	}

}
