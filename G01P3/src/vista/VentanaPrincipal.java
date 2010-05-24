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
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.math.plot.Plot2DPanel;

import ag.Problema;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTabbedPane  m_tabbedPane        = new JTabbedPane();
	private Plot2DPanel  m_grafica1          = new Plot2DPanel();
	private Plot2DPanel  m_grafica2          = new Plot2DPanel();
	private Plot2DPanel  m_grafica3          = new Plot2DPanel();
	private JComboBox    m_seleccion         = new JComboBox();
	private JProgressBar m_progressBar       = new JProgressBar();
	private JTextField   m_maxGen            = new JTextField();
	private JTextField   m_probCruce         = new JTextField();
	private JTextField   m_probMutacion      = new JTextField();
	private JTextField   m_tamPoblacion      = new JTextField();
	private JTextField   m_elitismo          = new JTextField();
	private JTextField   m_profundidad       = new JTextField();
	private JCheckBox    m_tieneIf           = new JCheckBox("IF");

	private JButton      m_lanzar            = new JButton();
	private JPanel       m_panelPrincipal    = null;
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
		setSize(800, 600);
		setLocationRelativeTo(getRootPane());
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		m_maxGen.setText("150");
		m_probCruce.setText("0.7");
		m_probMutacion.setText("0.02");
		m_tamPoblacion.setText("150");
		m_elitismo.setText("0.1");
		m_profundidad.setText("3");

		m_tieneIf.setSelected(true);

		m_resultado.setOpaque(false);
		m_resultado.setEditable(false);
		m_resultado.setBackground(new Color(0,0,0,0));
		m_resultado.setText("\t(Resultado no generado)");
		m_grafica1.removeAllPlots();
		m_grafica2.removeAllPlots();
		m_grafica3.removeAllPlots();
		m_progressBar.setValue(0);
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

	public double probCruce() {
		return Double.valueOf(m_probCruce.getText());
	}

	public double probMutacion() {
		return Double.valueOf(m_probMutacion.getText());
	}

	public int tamPoblacion() {
		return Integer.valueOf(m_tamPoblacion.getText());
	}

	public double elitismo() {
		return Double.valueOf(m_elitismo.getText());
	}

	public boolean tieneIf() {
		return m_tieneIf.isSelected();
	}

	public int profundidadMaxima() {
		return Integer.valueOf(m_profundidad.getText());
	}

	public JTextArea resultado() {
		return m_resultado;
	}

	public void terminado() {
		activaODesactivaTodo(m_panelPrincipal, true);
	}
	
	public int seleccionSeleccionada() {
		return m_seleccion.getSelectedIndex();
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

	private JComponent problema() {
        JPanel p = new JPanel();
		p.setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 10;
		gridBagConstraints.insets = new Insets(3, 3, 3, 3);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 1;
		p.add(new JLabel("Método de Selección"), gridBagConstraints);

		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridwidth = 4;
		final String[] metodoSeleccion = { "Ruleta", "Torneo", "Ranking" };
		m_seleccion = new JComboBox(metodoSeleccion);
		m_seleccion.setSelectedIndex(1);
		p.add(m_seleccion, gridBagConstraints);

		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 1;

		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 1;

		gridBagConstraints.gridwidth = 4;

		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;

		gridBagConstraints.gridy = 5;

		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.gridx = 0;

		gridBagConstraints.gridx = 1;
		p.add(new JLabel("Tamaño de Población"), gridBagConstraints);

		gridBagConstraints.gridx = 2;
		p.add(m_tamPoblacion, gridBagConstraints);

		gridBagConstraints.gridx = 3;

		gridBagConstraints.gridx = 4;

		gridBagConstraints.gridy = 7;
		gridBagConstraints.gridx = 0;

		gridBagConstraints.gridx = 1;
		p.add(new JLabel("Número Máximo de Generaciones"), gridBagConstraints);

		gridBagConstraints.gridx = 2;
		p.add(m_maxGen, gridBagConstraints);

		gridBagConstraints.gridx = 3;

		gridBagConstraints.gridx = 4;

		gridBagConstraints.gridy = 8;
		gridBagConstraints.gridx = 0;

		gridBagConstraints.gridx = 1;
		p.add(new JLabel("Probabilidad de Cruce"), gridBagConstraints);

		gridBagConstraints.gridx = 2;
		p.add(m_probCruce, gridBagConstraints);

		gridBagConstraints.gridx = 3;

		gridBagConstraints.gridx = 4;

		gridBagConstraints.gridy = 9;
		gridBagConstraints.gridx = 0;

		gridBagConstraints.gridx = 1;
		p.add(new JLabel("Probabilidad de Mutación"), gridBagConstraints);

		gridBagConstraints.gridx = 2;
		p.add(m_probMutacion, gridBagConstraints);

		gridBagConstraints.gridx = 3;

		gridBagConstraints.gridx = 4;

		gridBagConstraints.gridy = 10;
		gridBagConstraints.gridx = 0;

		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridx = 4;

		gridBagConstraints.gridy = 11;
		gridBagConstraints.gridx = 0;

		gridBagConstraints.gridx = 1;
		p.add(new JLabel("Elitismo"), gridBagConstraints);

		gridBagConstraints.gridx = 2;
		p.add(m_elitismo, gridBagConstraints);

		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 12;
		p.add(new JLabel("Profundidad"), gridBagConstraints);

		gridBagConstraints.gridx = 2;
		p.add(m_profundidad, gridBagConstraints);

		gridBagConstraints.gridy = 13;
		gridBagConstraints.gridx = 1;
		p.add(m_tieneIf, gridBagConstraints);

		gridBagConstraints.gridx = 2;

		gridBagConstraints.gridy = 14;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
		p.add(new JLabel("Resultado:"), gridBagConstraints);

		gridBagConstraints.gridy = 15;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.weighty = 10;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
		JScrollPane resultado = new JScrollPane(m_resultado, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		p.add(resultado, gridBagConstraints);

		gridBagConstraints.gridy = 16;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.weighty = 0;
		m_lanzar = new JButton("Lanzar");
		p.add(m_lanzar, gridBagConstraints);

		final VentanaPrincipal v = this;
		m_lanzar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ag.Problema p = new Problema();
				m_progressBar.setValue(0);
				m_resultado.setText("\tGenerando resultado...");
				activaODesactivaTodo(v.m_panelPrincipal, false);
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
