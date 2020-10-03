package Scripts;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class FrameMaker {

	public JFrame makeMenu() {
		JLabel emptyLabel = new JLabel("Menu");
		JButton start = new JButton("Start simulation");
		JButton generate = new JButton("Generate data");
		JButton exit = new JButton("Exit");
		
		JFrame frame = new JFrame("Menu");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);
		frame.getContentPane().add(start, BorderLayout.CENTER);
		frame.getContentPane().add(generate, BorderLayout.CENTER);
		frame.getContentPane().add(exit, BorderLayout.CENTER);
		frame.setSize(new Dimension(500,500));
		
		
		return frame;
	}
}
