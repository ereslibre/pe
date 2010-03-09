package vista;

import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import org.math.plot.Plot2DPanel;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTabbedPane m_tabbedPane = new JTabbedPane();
	private Plot2DPanel m_grafica1 = new Plot2DPanel();
	private Plot2DPanel m_grafica2 = new Plot2DPanel();
	private Plot2DPanel m_grafica3 = new Plot2DPanel();

	VentanaPrincipal() {
		super();
		m_tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		m_tabbedPane.addTab("Problema", problema());
		m_tabbedPane.addTab("Algoritmo", grafica1());
		m_tabbedPane.addTab("Aptitud", grafica2());
		m_tabbedPane.addTab("Presión Selectiva", grafica3());
		setLayout(new GridLayout(1, 1));
		add(m_tabbedPane);
		setSize(640, 480);
		setLocationRelativeTo(getRootPane());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	JComponent problema() {
		return new JLabel("Problema");
	}

	JComponent grafica1() {
		m_grafica1.setAxisLabel(0, "Generación");
		m_grafica1.setAxisLabel(1, "Máximo/Mínimo");
		return m_grafica1;
	}

	JComponent grafica2() {
		m_grafica2.setAxisLabel(0, "Generación");
		m_grafica2.setAxisLabel(1, "Media/Máxima");
		return m_grafica2;
	}

	JComponent grafica3() {
		m_grafica3.setAxisLabel(0, "Generación");
		m_grafica3.setAxisLabel(1, "Presión selectiva");
		return m_grafica3;
	}

}
