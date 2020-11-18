package Scripts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.html.StyleSheet.ListPainter;

public class FrameMaker {

	public ReadData readData;
	public Simulation sim;

	private static boolean write;
	public static JTextArea ta = new JTextArea();
	public static JTextPane tp = new JTextPane();
	public static JScrollPane sp = new JScrollPane(tp);
	

	public static JLabel speedLabel = new JLabel();
	public static JLabel dayLabel = new JLabel();
	public static JLabel actPrice = new JLabel();

	public FrameMaker(ReadData readData, Simulation sim) {
		this.readData = readData;
		this.sim = sim;
		write = true;
	}

	public JFrame makeMenu() {
		JLabel emptyLabel = new JLabel("Menu");
		JButton start = new JButton("Start simulation");
		JButton generate = new JButton("Generate data");
		JButton settings = new JButton("Settings");
		JButton exit = new JButton("Exit");

		ta = new JTextArea(16, 58);
		
		ta.setEditable(false); // set textArea non-editable
		JScrollPane scroll = new JScrollPane(ta);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String path = getFile();
				boolean succ = false;
				if (path != null) {
					readData.setPath(path);

					try {
						FrameManager.closeFrame(FrameManager.frameMenu);
						FrameManager.frameSim = simulationWindow();
						FrameManager.openFrame(FrameManager.frameSim);
						readData.setDataToSimulation();

					} catch (FileNotFoundException e1) {
						JOptionPane.showMessageDialog(null, "Chyba se souborem");
						System.out.println(e1.getMessage());
					}
				}

			}
		});
		
		settings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FrameManager.frameSettings = settingsWindow();
				FrameManager.openFrame(FrameManager.frameSettings);
			}
		});

		JFrame frame = new JFrame("Menu");
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		frame.setTitle("Menu");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel.add(emptyLabel);
		panel.add(start);
		panel.add(generate);
		panel.add(settings);
		panel.add(exit);

		frame.add(panel);
		frame.setLocationRelativeTo(null);
		frame.setSize(500, 500);
		frame.pack();

		return frame;
	}

	public JFrame simulationWindow() {
		JFrame frame = new JFrame("Simulation");
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
		JButton back = new JButton("Back");
		JButton play = new JButton("Play");
		JButton letSim = new JButton("Fast-forward");
		JButton slow = new JButton("Slow down");
		JButton speed = new JButton("Speed up");
		speedLabel.setText("Sim speed: " + String.valueOf((float)Settings.loopDelay / 1000));
		dayLabel.setText("Day: " + sim.day+1);
		actPrice.setText("Actual price: " + sim.totalCost);

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FrameManager.closeFrame(FrameManager.frameSim);
				FrameManager.openFrame(FrameManager.frameMenu);
				sim.cancel = true;
			}
		});

		play.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sim.setUpSimulation();
				// sim.startSimulation();
				sim.canSimulate = true;
				speedLabel.setText("Sim speed: " + String.valueOf((float)Settings.loopDelay / 1000));
				dayLabel.setText("Day: " + String.valueOf(sim.day+1));
				actPrice.setText("Actual price: " + String.valueOf(sim.totalCost));
			}
		});

		slow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Settings.addLoopDelay();
				speedLabel.setText("Sim speed: " + String.valueOf((float)Settings.loopDelay / 1000));
			}
		});

		speed.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Settings.takeLoopDelay();
				speedLabel.setText("Sim speed: " + String.valueOf((float)Settings.loopDelay / 1000));
			}
		});

		letSim.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Settings.letFree();
			}
		});

		JScrollPane scroll = new JScrollPane(ta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		panel.add(back);
		panel.add(play);
		panel.add(letSim);
		panel.add(slow);
		panel.add(speed);
		panel.add(actPrice);
		panel.add(dayLabel);
		panel.add(speedLabel);
		panel.add(sp);

		frame.add(panel);
		frame.setLocationRelativeTo(null);
		frame.setSize(500, 500);
		frame.pack();
		return frame;
	}

	public String getFile() {
		JFileChooser fileChooser = new JFileChooser("d:");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
		fileChooser.setFileFilter(filter);
		int result = fileChooser.showOpenDialog(null);
		File selectedFile = null;
		if (result == JFileChooser.APPROVE_OPTION) {
			selectedFile = fileChooser.getSelectedFile();

			if (!selectedFile.getName().toLowerCase().endsWith(".txt"))
				return null;
			else {
				JOptionPane.showMessageDialog(null, "Soubor vybrán");
				return selectedFile.getPath();
			}
		}
		return null;
	}
	
	public JFrame settingsWindow() {
		JFrame frame = new JFrame("Settings");
		JLabel label = new JLabel("settings");
		JButton back = new JButton("Back");
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FrameManager.closeFrame(FrameManager.frameSettings);
			}
		});
		
		panel.add(label);
		panel.add(back);
		
		frame.add(panel);
		frame.setLocationRelativeTo(null);
		frame.setSize(500, 500);
		frame.pack();
		return frame;
	}

	public static void appendTA(String text, Color color) {
		/*if (write) {
			ta.insert(text + "\n", 0);
			ta.update(ta.getGraphics());
		}*/
		Document doc = tp.getDocument();
		 SimpleAttributeSet attributeSet = new SimpleAttributeSet();  
		 StyleConstants.setForeground(attributeSet, color);  
		
		try {
			doc.insertString(0, text, attributeSet);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

}
