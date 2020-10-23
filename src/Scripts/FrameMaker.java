package Scripts;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.html.StyleSheet.ListPainter;

public class FrameMaker {

	public JFrame makeMenu() {
		JLabel emptyLabel = new JLabel("Menu");
		JButton start = new JButton("Start simulation");
		JButton generate = new JButton("Generate data");
		JButton exit = new JButton("Exit");
		
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getFile();			
			}
		});
		
		JFrame frame = new JFrame("Menu");
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		frame.setTitle("Menu");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		panel.add(start);
		panel.add(generate);
		panel.add(exit);
		
		frame.add(panel);
		frame.setLocationRelativeTo(null);
		frame.setSize(500, 500);
		frame.pack();
		
		return frame;
	}
	
	public void getFile() {
		JFileChooser fileChooser = new JFileChooser("d:");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
		fileChooser.setFileFilter(filter);
		int result = fileChooser.showOpenDialog(null);
		File selectedFile = null;
		if (result == JFileChooser.APPROVE_OPTION) {
			selectedFile = fileChooser.getSelectedFile();
			
			if (!selectedFile.getName().toLowerCase().endsWith(".txt"))
				return;
		}
		
	}
}
