package Scripts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
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
import java.awt.Font;

/**
 * Trida vytvarejici okna apliakce
 * 
 * @author Jan Kubice & Michaela Benešová
 *
 */
public class FrameMaker {

	public ReadData readData;
	public Simulation sim;

	private static boolean writeToTP;
	public static JTextPane tp = new JTextPane();
	public static JScrollPane sp = new JScrollPane(tp);

	public static JLabel speedLabel = new JLabel();
	public static JLabel dayLabel = new JLabel();
	public static JLabel actPrice = new JLabel();

	public FrameMaker(ReadData readData, Simulation sim) {
		this.readData = readData;
		this.sim = sim;
		writeToTP = true;
	}

	public JFrame makeMenu() {
		JLabel menuLabel = new JLabel("Menu");
		JButton start = new JButton("Start simulation");
		JButton settings = new JButton("Settings");
		JButton exit = new JButton("Exit");
		
		menuLabel.setFont(new Font("Verdana", Font.PLAIN, 25));
		
		menuLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		start.setAlignmentX(Component.CENTER_ALIGNMENT);
		settings.setAlignmentX(Component.CENTER_ALIGNMENT);
		exit.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		start.setMaximumSize(new Dimension(150, 30));
		settings.setMaximumSize(new Dimension(150, 30));
		exit.setMaximumSize(new Dimension(150, 30));

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
				FrameManager.closeFrame(FrameManager.frameMenu);
			}
		});
		
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		JFrame frame = new JFrame("Menu");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setAlignmentX(Component.CENTER_ALIGNMENT);

		frame.setTitle("Menu");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel.add(Box.createRigidArea(new Dimension(0, 120)));
		panel.add(menuLabel);
		panel.add(Box.createRigidArea(new Dimension(0, 5)));
		panel.add(start);
		panel.add(Box.createRigidArea(new Dimension(0, 5)));
		panel.add(settings);
		panel.add(Box.createRigidArea(new Dimension(0, 5)));
		panel.add(exit);

		
		frame.add(panel);
		frame.setLocationRelativeTo(null);
		frame.setSize(500, 500);
		frame.setResizable(false);
		return frame;
	}

	/**
	 * Vytvori okno simulace
	 * @return okno simulace
	 */
	public JFrame simulationWindow() {
		JFrame frame = new JFrame("Simulation");
		frame.setResizable(false);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JButton back = new JButton("Back");
		JButton play = new JButton("Play");
		JButton letSim = new JButton("Fast-forward");
		JButton slow = new JButton("Slow down");
		JButton speed = new JButton("Speed up");
		JButton write = new JButton("Write - OFF");
		speedLabel.setText("Sim speed: " + String.valueOf((float) Settings.getLoopDelay() / 1000));
		dayLabel.setText("Day: " + sim.day + 1);
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
				String name = WriteData.createFile();
				WriteData.openFile(name, true);
				sim.setUpSimulation();
				// sim.startSimulation();
				sim.canSimulate = true;
				speedLabel.setText("Sim speed: " + String.valueOf((float) Settings.getLoopDelay() / 1000));
				dayLabel.setText("Day: " + String.valueOf(sim.day + 1));
				actPrice.setText("Actual price: " + String.valueOf(sim.totalCost));
			}
		});

		slow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Settings.addLoopDelay();
				speedLabel.setText("Sim speed: " + String.valueOf((float) Settings.getLoopDelay() / 1000));
			}
		});

		speed.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Settings.takeLoopDelay();
				speedLabel.setText("Sim speed: " + String.valueOf((float) Settings.getLoopDelay() / 1000));
			}
		});

		letSim.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Settings.letFree();
			}
		});

		write.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (write.getText() == "Write - OFF") {
					write.setText("Write - ON");
					Settings.letFree();
				}
				else {
					write.setText("Write - OFF");
					Settings.unLetFree();
				}
				
				writeToTP = !writeToTP;
			}
		});

		panel.add(back);
		panel.add(play);
		panel.add(letSim);
		panel.add(slow);
		panel.add(speed);
		panel.add(write);
		panel.add(actPrice);
		panel.add(dayLabel);
		panel.add(speedLabel);
		panel.add(sp);

		frame.add(panel);
		frame.setLocationRelativeTo(null);
		frame.setSize(700, 500);
		return frame;
	}

	/**
	 * Otevre okno pro vyber scenare simulace
	 * @return cesta ke zvolenemu adresari
	 */
	public String getFile() {
		JFileChooser fileChooser = new JFileChooser(ReadData.getPath());
		System.out.println(ReadData.getPath());
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
				return selectedFile.getPath() + "\\";
			}
		}
		return null;
	}

	/**
	 * Vytvori okno nastaveni
	 * @return okno nastaveni
	 */
	public JFrame settingsWindow() {
		JFrame frame = new JFrame("Settings");
		JLabel label = new JLabel("Settings");
		JLabel filePathText = new JLabel("File with txts: ");
		JLabel filePath = new JLabel(Settings.getPath());
		JButton setPath = new JButton("Choose");
		
		JLabel savePathText = new JLabel("File to save txts: ");
		JLabel savePath = new JLabel(Settings.getSavePath());
		JButton setSavePath = new JButton("Choose");

		JButton back = new JButton("Back");
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		label.setFont(new Font("Verdana", Font.PLAIN, 25));
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		filePathText.setFont(new Font("Verdana", Font.PLAIN, 15));
		filePathText.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		savePathText.setFont(new Font("Verdana", Font.PLAIN, 15));
		savePathText.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		filePath.setFont(new Font("Verdana", Font.PLAIN, 15));
		filePath.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		savePath.setFont(new Font("Verdana", Font.PLAIN, 15));
		savePath.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		setPath.setAlignmentX(Component.CENTER_ALIGNMENT);
		setSavePath.setAlignmentX(Component.CENTER_ALIGNMENT);
		back.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		setPath.setMaximumSize(new Dimension(150, 30));
		setSavePath.setMaximumSize(new Dimension(150, 30));
		back.setMaximumSize(new Dimension(150, 30));
		
		

		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FrameManager.closeFrame(FrameManager.frameSettings);
				FrameManager.openFrame(FrameManager.frameMenu);
			}
		});

		setPath.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser f = new JFileChooser();
				f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				f.showSaveDialog(null);
				Settings.setPath(f.getSelectedFile().toString());
				readData.setPath(f.getSelectedFile().toString());
				filePath.setText(f.getSelectedFile().toString());
			}
		});

		setSavePath.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser f = new JFileChooser();
				f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				f.showSaveDialog(null);
				Settings.setSavePath(f.getSelectedFile().toString() + "\\");
				savePath.setText(f.getSelectedFile().toString());
			}
		});

		panel.add(label);
		panel.add(Box.createRigidArea(new Dimension(0, 5)));
		panel.add(filePathText);
		panel.add(Box.createRigidArea(new Dimension(0, 5)));
		panel.add(filePath);
		panel.add(Box.createRigidArea(new Dimension(0, 5)));
		panel.add(setPath);
		panel.add(Box.createRigidArea(new Dimension(0, 5)));
		panel.add(savePathText);
		panel.add(Box.createRigidArea(new Dimension(0, 5)));
		panel.add(savePath);
		panel.add(Box.createRigidArea(new Dimension(0, 5)));
		panel.add(setSavePath);
		panel.add(Box.createRigidArea(new Dimension(0, 15)));
		panel.add(back);
		
		frame.add(panel);
		frame.setLocationRelativeTo(null);
		frame.setSize(500, 500);
		frame.setResizable(false);
		frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		return frame;
	}

	/**
	 * Vypise text do textpane v okne simulace
	 * @param text text vypisu
	 * @param color barva vypisu
	 */
	public static void appendTP(String text, Color color) {
		if (!writeToTP)
			return;
		
		Document doc = tp.getDocument();
		SimpleAttributeSet attributeSet = new SimpleAttributeSet();
		StyleConstants.setForeground(attributeSet, color);

		try {
			doc.insertString(0, text, attributeSet);
		} catch (BadLocationException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Vypise text do textpane v okne simulace
	 * @param text text vypisu
	 * @param color barva vypisu
	 * @param bold zda ma byt text tucny
	 */
	public static void appendTP(String text, Color color, boolean bold) {
		if (!writeToTP)
			return;
		
		Document doc = tp.getDocument();
		SimpleAttributeSet attributeSet = new SimpleAttributeSet();
		StyleConstants.setForeground(attributeSet, color);
		StyleConstants.setBold(attributeSet, bold);

		try {
			doc.insertString(0, text, attributeSet);
		} catch (BadLocationException e) {
			System.out.println(e.getMessage());
		}
	}

}
